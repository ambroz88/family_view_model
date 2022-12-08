package org.ambrogenea.familyview.service.impl.tree;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.DescendentTreeInfo;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.dto.tree.TreeModel;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;
import org.ambrogenea.familyview.service.TreeService;
import org.ambrogenea.familyview.service.impl.HorizontalConfigurationService;
import org.ambrogenea.familyview.service.impl.LineageServiceImpl;
import org.ambrogenea.familyview.service.impl.VerticalConfigurationService;
import org.ambrogenea.familyview.utils.Tools;

import java.util.List;
import java.util.stream.Collectors;

public class AllDescendentsTreeService implements TreeService {

    private final ConfigurationExtensionService extensionConfig;
    private final ConfigurationService configService;
    private LineageService lineageService;

    public AllDescendentsTreeService(ConfigurationService configurationService) {
        configService = configurationService;
        if (configurationService.isShowCouplesVertical()) {
            extensionConfig = new VerticalConfigurationService(configurationService);
        } else {
            extensionConfig = new HorizontalConfigurationService(configurationService);
        }
    }

    @Override
    public TreeModel generateTreeModel(AncestorPerson rootPerson, ConfigurationService configuration) {
        Position rootPosition = new Position();
        lineageService = new LineageServiceImpl(configuration);
        lineageService.addRootPerson(rootPosition, rootPerson);
        lineageService.addRootSpouses(rootPosition, rootPerson);

        lineageService.addLine(
                rootPosition.addXAndY((configuration.getAdultImageWidth() + extensionConfig.getMarriageLabelWidth()) / 2, 0),
                rootPosition.addXAndY((configuration.getAdultImageWidth() + extensionConfig.getMarriageLabelWidth()) / 2, (configuration.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                Relation.DIRECT
        );

        generateAllDescendents(new Position(Spaces.SIBLINGS_GAP, rootPosition.getY()),
                rootPerson.getSpouseCouples(), calculatePageWidth(rootPerson)
        );

        TreeModel treeModel = lineageService.getTreeModel();
        treeModel.setTreeName("Rozrod " + Tools.getNameIn2ndFall(rootPerson));
        return treeModel;
    }

    private int calculatePageWidth(AncestorPerson personModel) {
        DescendentTreeInfo treeInfo = personModel.getSpouseCouple().getDescendentTreeInfo();
        return Spaces.SIBLINGS_GAP + treeInfo.getMaxCouplesCount() * (extensionConfig.getCoupleWidth() + Spaces.SIBLINGS_GAP)
                + treeInfo.getMaxSinglesCount() * (configService.getAdultImageWidth() + Spaces.SIBLINGS_GAP);
    }

    public Position generateAllDescendents(Position firstChildPosition, List<AncestorCouple> spouseCouples, int allDescendentsWidth) {
        Position actualChildPosition = firstChildPosition.addXAndY(0,
                configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP
        );

        int descendentsWidth;
        for (AncestorCouple spouseCouple : spouseCouples) {
            int childrenCoupleCount = spouseCouple.getDescendentTreeInfo().getMaxCouplesCount();
            int childrenSinglesCount = spouseCouple.getDescendentTreeInfo().getMaxSinglesCount();
            descendentsWidth = childrenCoupleCount * (extensionConfig.getCoupleWidth() + Spaces.HORIZONTAL_GAP)
                    + childrenSinglesCount * (configService.getAdultImageWidth() + Spaces.HORIZONTAL_GAP);

            actualChildPosition = actualChildPosition.addXAndY(descendentsWidth / 2, 0);
            actualChildPosition = addCoupleFamily(actualChildPosition, spouseCouple, descendentsWidth);
        }

        return firstChildPosition.addXAndY(Math.max(configService.getAdultImageWidth(), allDescendentsWidth), 0);
    }

    public Position addCoupleFamily(Position parentCentralPosition, AncestorCouple couple, int allDescendentsWidth) {
        Position actualChildPosition = parentCentralPosition.addXAndY(-allDescendentsWidth / 2, 0);
        List<AncestorPerson> children = couple.getChildren();
        for (int i = 0; i < children.size(); i++) {
            AncestorPerson child = children.get(i);

            int allChildrenCoupleCount = calculateCouplesCount(child.getSpouseCouples());
            int allChildrenSinglesCount = calculateSinglesCount(child.getSpouseCouples());
            int descendentsWidth = allChildrenCoupleCount * (extensionConfig.getCoupleWidth() + Spaces.HORIZONTAL_GAP)
                    + allChildrenSinglesCount * (configService.getAdultImageWidth() + Spaces.HORIZONTAL_GAP);
            int childWidth = child.getSpouseCount() * extensionConfig.getCoupleWidth();

            boolean widerParents = false;
            if (descendentsWidth < childWidth) {
                actualChildPosition = actualChildPosition.addXAndY((childWidth - descendentsWidth) / 2, 0);
                widerParents = true;
            }

            Position endGenerationPosition = generateAllDescendents(actualChildPosition, child.getSpouseCouples(), descendentsWidth);
            int centerX = (endGenerationPosition.getX() - actualChildPosition.getX()) / 2;
            Position parent;
            if (child.getSpouse() != null || widerParents) {
                parent = actualChildPosition.addXAndY(centerX - extensionConfig.getSpouseDistance() / 2, 0);
            } else {
                parent = actualChildPosition.addXAndY(centerX, 0);
            }

            lineageService.addPerson(parent, child);
            Position spousePosition = new Position();//addSpouse(parent, child);

            addLineToChildren(child, parent);
            addLineFromChildren(children.size(), i, parent, parentCentralPosition);

            if (endGenerationPosition.getX() > spousePosition.getX()) {
                actualChildPosition = endGenerationPosition.addXAndY(Spaces.HORIZONTAL_GAP, 0);
            } else {
                actualChildPosition = spousePosition.addXAndY(configService.getAdultImageWidth() / 2 + Spaces.SIBLINGS_GAP, 0);
            }
        }
        return actualChildPosition;
    }

    private void addLineFromChildren(int childrenCount, int childrenIndex, Position childPosition, Position parentCentralPosition) {
        if (childrenCount == 1 || (childrenCount > 2 && childrenIndex > 0 && childrenIndex < childrenCount - 1)) {
            lineageService.addLine(childPosition,
                    childPosition.addXAndY(0, -(configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
        } else {
            lineageService.addLine(childPosition,
                    parentCentralPosition.addXAndY(0, -(configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
        }
    }

    private void addLineToChildren(AncestorPerson child, Position parent) {
        if (child.getSpouseCouple() != null && child.getSpouse() == null) {
            lineageService.addLine(parent,
                    parent.addXAndY(0, (configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
            lineageService.addHeraldry(parent.addXAndY(0, configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP), child.getBirthDatePlace().getSimplePlace());
        } else if (child.getSpouseCouple() != null && !child.getSpouseCouple().getChildren().isEmpty()) {
            lineageService.addLine(parent.addXAndY(extensionConfig.getSpouseDistance() / 2, 0),
                    parent.addXAndY(extensionConfig.getSpouseDistance() / 2, (configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP) / 2),
                    Relation.DIRECT
            );
            lineageService.addHeraldry(parent.addXAndY(extensionConfig.getSpouseDistance() / 2,
                    configService.getAdultImageHeightAlternative() + Spaces.VERTICAL_GAP), child.getBirthDatePlace().getSimplePlace()
            );
        }
    }

    private int calculateCouplesCount(List<AncestorCouple> spouseCouples) {
        return (int) spouseCouples.stream()
                .map(AncestorCouple::getDescendentTreeInfo)
                .collect(Collectors.summarizingInt(DescendentTreeInfo::getMaxCouplesCount))
                .getSum();
    }

    private int calculateSinglesCount(List<AncestorCouple> spouseCouples) {
        return (int) spouseCouples.stream()
                .map(AncestorCouple::getDescendentTreeInfo)
                .collect(Collectors.summarizingInt(DescendentTreeInfo::getMaxSinglesCount))
                .getSum();
    }

}

package org.ambrogenea.familyview.service.impl;

import org.ambrogenea.familyview.constant.Spaces;
import org.ambrogenea.familyview.dto.AncestorCouple;
import org.ambrogenea.familyview.dto.AncestorPerson;
import org.ambrogenea.familyview.dto.ParentsDto;
import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.enums.LabelShape;
import org.ambrogenea.familyview.enums.Relation;
import org.ambrogenea.familyview.service.ConfigurationExtensionService;
import org.ambrogenea.familyview.service.ConfigurationService;
import org.ambrogenea.familyview.service.LineageService;

import java.util.List;
import java.util.Objects;

public class LineageServiceImpl extends CommonAncestorServiceImpl implements LineageService {

    public LineageServiceImpl(ConfigurationService configurationService) {
        super(configurationService);
    }

    @Override
    public void generateSpouseAndSiblings(Position rootPersonPosition, AncestorPerson rootPerson) {
        if (getConfiguration().isShowSpouses()) {
            Position lastSpouse = addRootSpouses(rootPersonPosition, rootPerson);
            if (getConfiguration().isShowSiblings()) {
                addSiblingsAroundWives(rootPersonPosition, rootPerson, lastSpouse.getX());
            }
        } else if (getConfiguration().isShowSiblings()) {
            addSiblings(rootPersonPosition, rootPerson);
        }
    }

    @Override
    public void generateFathersFamily(Position heraldryPosition, AncestorPerson person) {
        if (person != null) {
            ParentsDto parents = generateParents(heraldryPosition, person);
            if (parents != null) {
                Position parentPosition = Objects.requireNonNullElse(parents.getHusbandPosition(), parents.getWifePosition());

                Position nextHeraldryPosition = new Position(parentPosition.getX(), parents.getNextHeraldryY());
                addLine(parentPosition, nextHeraldryPosition, Relation.DIRECT);

                AncestorPerson parent = Objects.requireNonNullElse(person.getFather(), person.getMother());
                if (configService.isShowSiblings()) {
                    addSiblings(parentPosition, parent);
                    if (!parent.getYoungerSiblings().isEmpty()) {
                        addLineAboveSpouse(parentPosition, parents.getNextHeraldryY());
                    }
                }

                generateFathersFamily(nextHeraldryPosition, parent);
            }
        }
    }

    @Override
    public void generateMotherFamily(Position heraldryPosition, AncestorPerson person) {
        if (person != null) {
            ParentsDto parents = generateSwitchedParents(heraldryPosition, person);
            if (parents != null) {
                Position parentPosition = Objects.requireNonNullElse(parents.getWifePosition(), parents.getHusbandPosition());

                Position nextHeraldryPosition = new Position(parentPosition.getX(), parents.getNextHeraldryY());
                addLine(parentPosition, nextHeraldryPosition, Relation.DIRECT);

                AncestorPerson parent = Objects.requireNonNullElse(person.getMother(), person.getFather());
                if (configService.isShowSiblings()) {
                    addSiblings(parentPosition, parent);
                    if (!parent.getYoungerSiblings().isEmpty()) {
                        addLineAboveSpouse(parentPosition, parents.getNextHeraldryY());
                    }
                }

                generateFathersFamily(nextHeraldryPosition, parent);
            }
        }
    }
    public ParentsDto generateSwitchedParents(Position heraldryPosition, AncestorPerson child) {
        if (configService.isShowCouplesVertical()) {
            return generateSwitchedParents(heraldryPosition, child, new VerticalConfigurationService(configService));
        } else {
            return generateSwitchedParents(heraldryPosition, child, new HorizontalConfigurationService(configService));
        }
    }

    private ParentsDto generateSwitchedParents(Position heraldryPosition, AncestorPerson child, ConfigurationExtensionService extensionConfig) {
        this.extensionConfig = extensionConfig;

        Position motherPosition;
        if (child.getMother() != null) {
            motherPosition = heraldryPosition.addXAndY(
                    -extensionConfig.getFatherHorizontalDistance(),
                    -extensionConfig.getFatherVerticalDistance()
            );
            treeModelService.addPerson(motherPosition, child.getMother());
        } else {
            motherPosition = null;
        }

        Position fatherPosition;
        if (child.getFather() != null) {
            int fatherXShift;
            int fatherYShift;
            if (motherPosition == null) {
                fatherXShift = 0;
                fatherYShift = extensionConfig.getFatherVerticalDistance();
            } else {
                fatherXShift = extensionConfig.getMotherHorizontalDistance();
                fatherYShift = extensionConfig.getMotherVerticalDistance();
            }
            fatherPosition = heraldryPosition.addXAndY(fatherXShift, -fatherYShift);
            treeModelService.addPerson(fatherPosition, child.getFather());
        } else {
            fatherPosition = null;
        }

        return getParentsDto(heraldryPosition, fatherPosition, motherPosition, child, extensionConfig.getGenerationsVerticalDistance());
    }

    @Override
    public void generateParentsFamily(Position heraldryPosition, AncestorPerson person) {
        ParentsDto parentsDto = generateHorizontalParents(heraldryPosition, person);
        addFatherFamily(person.getFather(), parentsDto);
        addMotherFamily(person.getMother(), parentsDto);
    }

    private void addFatherFamily(AncestorPerson father, ParentsDto parentsDto) {
        int fatherSiblingsWidth = 0;
        if (configService.isShowSiblings() && father.getFather() != null) {
            int fathersSiblings = father.getFather().getMaxYoungerSiblings();
            if (fathersSiblings > 0) {
                fatherSiblingsWidth = fathersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }
        int fatherHeraldryX = parentsDto.getHusbandPosition().getX() - extensionConfig.getMotherHorizontalDistance() - fatherSiblingsWidth;

        Position fatherHeraldry = new Position(fatherHeraldryX, parentsDto.getNextHeraldryY());
        generateFathersFamily(fatherHeraldry, father);
        addLine(fatherHeraldry, parentsDto.getHusbandPosition(), Relation.DIRECT);
    }

    private void addMotherFamily(AncestorPerson mother, ParentsDto parentsDto) {
        int motherSiblingsWidth = 0;
        if (configService.isShowSiblings() && mother.getFather() != null) {
            int mothersSiblings = mother.getFather().getMaxOlderSiblings();
            if (mothersSiblings > 0) {
                motherSiblingsWidth = mothersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }

        int motherX = parentsDto.getWifePosition().getX() + extensionConfig.getMotherHorizontalDistance() + motherSiblingsWidth;

        Position motherHeraldry = new Position(motherX, parentsDto.getNextHeraldryY());
        generateFathersFamily(motherHeraldry, mother);
        addLine(motherHeraldry, parentsDto.getWifePosition(), Relation.DIRECT);
    }

    @Override
    public Position generateAllDescendents(Position firstChildPosition, List<AncestorCouple> spouseCouples, int allDescendentsWidth) {
        return null;
    }

    @Override
    public void addAllParents(Position heraldryPosition, AncestorPerson child) {
        ParentsDto parentsDto = generateHorizontalParents(heraldryPosition, child);
        ConfigurationExtensionService extensionService = new HorizontalConfigurationService(getConfiguration());

        if (child.getMother() != null) {
            AncestorPerson mother = child.getMother();
            int verticalShift = -extensionService.getMotherVerticalDistance();
            double motherParentsCount = Math.min(mother.getInnerParentsCount(), mother.getLastParentsCount());

            Position motherPosition;
            if (mother.getFather() == null) {
                motherPosition = heraldryPosition.addXAndY(0, verticalShift);
            } else {
                double fatherParentsCount = Math.min(child.getFather().getInnerParentsCount(), child.getFather().getLastParentsCount());

                Position fatherPosition;
                if (motherParentsCount == 0 || fatherParentsCount == 0) {
                    fatherPosition = heraldryPosition.addXAndY(-extensionService.getHalfSpouseLabelSpace(), verticalShift);
                    motherPosition = heraldryPosition.addXAndY(extensionService.getHalfSpouseLabelSpace(), verticalShift);
                } else {
                    double motherParentWidth = (extensionService.getCoupleWidth() + Spaces.SIBLINGS_GAP) * motherParentsCount;
                    double fatherParentWidth = (extensionService.getCoupleWidth() + Spaces.SIBLINGS_GAP) * fatherParentsCount;
                    int halfParentWidth = (int) (fatherParentWidth + motherParentWidth) / 2;
                    fatherPosition = heraldryPosition.addXAndY(-halfParentWidth, verticalShift);
                    motherPosition = heraldryPosition.addXAndY(halfParentWidth, verticalShift);
                }

                addPerson(fatherPosition, child.getFather());
                Position labelPosition = fatherPosition.addXAndY(getConfiguration().getAdultImageWidth() / 2 + Spaces.LABEL_GAP,
                        -extensionService.getMarriageLabelHeight() / 2);
                int labelWidth;
                if (configService.getLabelShape().equals(LabelShape.OVAL)) {
                    labelWidth = motherPosition.getX() - fatherPosition.getX() - getConfiguration().getAdultImageWidth() - 2 * Spaces.LABEL_GAP;
                } else {
                    labelWidth = motherPosition.getX() - fatherPosition.getX();
                }
                addLabel(labelPosition, labelWidth,
                        child.getParents().getDatePlace().getLocalizedDate(getConfiguration().getLocale())
                );
                addAllParents(fatherPosition, child.getFather());
            }

            if (getConfiguration().isShowHeraldry()) {
                addHeraldry(heraldryPosition, child.getBirthDatePlace().getSimplePlace());
            }

            addPerson(motherPosition, child.getMother());
            addAllParents(motherPosition, child.getMother());
            addVerticalLineToParents(heraldryPosition);
        }
    }

    @Override
    public Position addCoupleFamily(Position firstChildPosition, AncestorCouple couple, int descendentsWidth) {
        return null;
    }

    @Override
    public Position addMother(Position childPosition, AncestorPerson mother, String marriageDate) {
        return null;
    }

    @Override
    public Position addFather(Position childPosition, AncestorPerson father) {
        return null;
    }

    @Override
    public Position addSpouse(Position rootPosition, AncestorPerson root) {
        return null;
    }

    @Override
    public void addSiblingsAroundMother(Position rootSibling, AncestorPerson rootChild) {

    }

    @Override
    public void addSiblingsAroundWives(Position rootSibling, AncestorPerson rootChild, int lastSpouseX) {
        int spouseGap = lastSpouseX - rootSibling.getX();

        if (!rootChild.getYoungerSiblings().isEmpty()) {
            addLineAboveSpouses(rootSibling, spouseGap);
        }

        addOlderSiblings(rootSibling, rootChild);
        addYoungerSiblings(new Position(lastSpouseX, rootSibling.getY()), rootChild);
    }

    @Override
    public void addVerticalLineToParents(Position child) {

    }
}

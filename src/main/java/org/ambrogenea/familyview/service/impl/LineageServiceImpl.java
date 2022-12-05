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
            if (parents != null && parents.getHusbandPosition() != null) {
                int newFatherXPosition = parents.getHusbandPosition().getX();
                Position nextHeraldryPosition = new Position(newFatherXPosition, parents.getNextHeraldryY());
                addLine(parents.getHusbandPosition(), nextHeraldryPosition, Relation.DIRECT);

                generateFathersFamily(
                        nextHeraldryPosition,
                        Objects.requireNonNullElse(person.getFather(), person.getMother())
                );
            }
        }
    }

    @Override
    public void generateMotherFamily(Position heraldryPosition, AncestorPerson person) {
        if (person != null) {
            //TODO: switch position of parents
            ParentsDto parents = generateParents(heraldryPosition, person);
            if (parents != null && parents.getWifePosition() != null) {
                int newFatherXPosition = parents.getWifePosition().getX();
                Position nextHeraldryPosition = new Position(newFatherXPosition, parents.getNextHeraldryY());
                addLine(parents.getWifePosition(), nextHeraldryPosition, Relation.DIRECT);

                generateFathersFamily(
                        nextHeraldryPosition,
                        Objects.requireNonNullElse(person.getMother(), person.getFather())
                );
            }
        }
    }

    @Override
    public void generateParentsFamily(Position heraldryPosition, AncestorPerson person) {
        ParentsDto parentsDto = generateHorizontalParents(heraldryPosition, person);
        addFatherFamily(person.getFather(), parentsDto);
        addMotherFamily(person.getMother(), parentsDto);
    }

    private void addFatherFamily(AncestorPerson father, ParentsDto parentsDto) {
        int fatherSiblingsWidth = 0;
        if (father.getFather() != null) {
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
        if (mother.getFather() != null) {
            int mothersSiblings = mother.getFather().getMaxOlderSiblings();
            if (mothersSiblings > 0) {
                motherSiblingsWidth = mothersSiblings * (configService.getSiblingImageWidth() + Spaces.HORIZONTAL_GAP) + Spaces.SIBLINGS_GAP;
            }
        }

        ConfigurationExtensionService extensionService = new HorizontalConfigurationService(getConfiguration());
        int parentWidth = mother.getFatherGenerations() * extensionService.getHalfSpouseLabelSpace();

        int motherX = parentsDto.getWifePosition().getX() + extensionService.getMotherHorizontalDistance() + motherSiblingsWidth + parentWidth;
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
            int verticalShift = -getConfiguration().getAdultImageHeight() - Spaces.VERTICAL_GAP;
            double motherParentsCount = Math.min(child.getMother().getInnerParentsCount(), child.getMother().getLastParentsCount());

            Position motherPosition;
            if (child.getFather() == null) {
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
            addLineAboveSpouse(rootSibling, spouseGap);
        }

        addOlderSiblings(rootSibling, rootChild);
        addYoungerSiblings(new Position(lastSpouseX, rootSibling.getY()), rootChild);
    }

    @Override
    public void addVerticalLineToParents(Position child) {

    }
}

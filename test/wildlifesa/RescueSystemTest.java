package wildlifesa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RescueSystemTest {
    private RescueSystem system;
    private InjuredAnimalRescue injured;

    @Before
    public void setUp() {
        system = new RescueSystem();
        injured = new InjuredAnimalRescue("R001", "Leo", "Lion", "Kruger Park",
                "Ranger Mokoena", 5, 200, RescueStatus.RECORDED, "Broken leg", 3000, true);
    }

    @Test
    public void injuredRescueCostIncludesCareTreatmentAndSurgery() {
        assertEquals(9000.00, injured.calculateTotalRescueCost(), 0.001);
    }

    @Test
    public void orphanedRescueCostIncludesFeedingAndFosterCare() {
        RescueCase orphan = new OrphanedAnimalRescue("R002", "Tiny", "Elephant",
                "Limpopo", "Ranger Dube", 4, 150, RescueStatus.RECORDED, 3, 1200, true);
        assertEquals(4300.00, orphan.calculateTotalRescueCost(), 0.001);
    }

    @Test
    public void endangeredRescueCostIncludesSecurityAndSpecialistTeam() {
        RescueCase endangered = new EndangeredSpeciesRescue("R003", "Hope", "Rhino",
                "Hluhluwe", "Ranger Naidoo", 2, 500, RescueStatus.RECORDED,
                "Critically Endangered", 4000, true);
        assertEquals(13000.00, endangered.calculateTotalRescueCost(), 0.001);
    }

    @Test
    public void prioritiesAreCalculatedForEachRescueType() {
        RescueCase orphan = new OrphanedAnimalRescue("R002", "Tiny", "Elephant",
                "Limpopo", "Ranger Dube", 4, 150, RescueStatus.RECORDED, 3, 1200, true);
        RescueCase endangered = new EndangeredSpeciesRescue("R003", "Hope", "Rhino",
                "Hluhluwe", "Ranger Naidoo", 2, 500, RescueStatus.RECORDED,
                "Critically Endangered", 4000, true);
        assertEquals("CRITICAL", injured.determineRescuePriority());
        assertEquals("HIGH", orphan.determineRescuePriority());
        assertEquals("CRITICAL", endangered.determineRescuePriority());
    }

    @Test
    public void olderOrphanWithoutFosterCareHasMediumPriority() {
        RescueCase orphan = new OrphanedAnimalRescue("R004", "Bongi", "Giraffe",
                "North West", "Ranger Molefe", 3, 180, RescueStatus.RECORDED, 15, 900, false);
        assertEquals("MEDIUM", orphan.determineRescuePriority());
    }

    @Test
    public void endangeredCaseWithoutSpecialistTeamHasHighPriority() {
        RescueCase endangered = new EndangeredSpeciesRescue("R005", "Sky", "Vulture",
                "Free State", "Ranger Nkosi", 2, 220, RescueStatus.RECORDED,
                "Endangered", 1500, false);
        assertEquals("HIGH", endangered.determineRescuePriority());
    }

    @Test
    public void startAndCompleteUpdateStatus() {
        injured.startRescueOperation();
        assertEquals(RescueStatus.IN_PROGRESS, injured.getCurrentRescueStatus());
        injured.completeRescueOperation();
        assertEquals(RescueStatus.COMPLETED, injured.getCurrentRescueStatus());
    }

    @Test
    public void searchesForExistingRescueCase() {
        system.addRescueCase(injured);
        assertSame(injured, system.searchRescueCase("R001"));
        assertNull(system.searchRescueCase("R999"));
    }

    @Test
    public void preventsDuplicateRescueCaseIds() {
        assertTrue(system.addRescueCase(injured));
        RescueCase duplicate = new InjuredAnimalRescue("r001", "Simba", "Lion",
                "Pretoria", "Ranger Smith", 2, 100, RescueStatus.RECORDED, "Cut", 500, false);
        assertFalse(system.addRescueCase(duplicate));
        assertEquals(1, system.getAllRescueCases().size());
    }

    @Test
    public void rescueStatusEnumRejectsNullAndContainsOnlyValidStatuses() {
        try {
            injured.setCurrentRescueStatus(null);
            fail("A null status should be rejected.");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("status"));
        }
        assertArrayEquals(new RescueStatus[]{RescueStatus.RECORDED,
            RescueStatus.IN_PROGRESS, RescueStatus.COMPLETED}, RescueStatus.values());
    }

    @Test
    public void updateStatusHandlesExistingAndMissingCases() {
        system.addRescueCase(injured);
        assertTrue(system.updateRescueStatus("R001", RescueStatus.IN_PROGRESS));
        assertEquals(RescueStatus.IN_PROGRESS, injured.getCurrentRescueStatus());
        assertFalse(system.updateRescueStatus("R999", RescueStatus.COMPLETED));
        assertFalse(system.updateRescueStatus("R001", null));
    }

    @Test
    public void reportContainsTotalsAndCaseInformation() {
        system.addRescueCase(injured);
        String report = system.generateRescueReport();
        assertTrue(report.contains("R001"));
        assertTrue(report.contains("Total number of rescue cases: 1"));
        assertTrue(report.contains("R9000.00"));
    }

    @Test
    public void validatesBlankAndNonPositiveValues() {
        try {
            new InjuredAnimalRescue("", "Leo", "Lion", "Kruger", "Ranger",
                    5, 200, RescueStatus.RECORDED, "Injury", 100, false);
            fail("A blank Rescue Case ID should be rejected.");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("Rescue Case ID"));
        }

        try {
            new InjuredAnimalRescue("R010", "Leo", "Lion", "Kruger", "Ranger",
                    0, 200, RescueStatus.RECORDED, "Injury", 100, false);
            fail("Zero rescue days should be rejected.");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("greater than zero"));
        }
    }
}

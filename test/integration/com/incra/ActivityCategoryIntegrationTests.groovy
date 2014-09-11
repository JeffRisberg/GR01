package com.incra

import grails.test.GrailsUnitTestCase

class ActivityCategoryIntegrationTests extends GrailsUnitTestCase {

    void testFirstSaveEver() {
        def actCat = new ActivityCategory(name: 'Cat1')
        assertNotNull actCat.save()
        assertNotNull actCat.id

        def foundActCat = ActivityCategory.get(actCat.id)
        assertEquals 'Cat1', foundActCat.name
    }

    void testSaveAndUpdate() {
        def actCat = new ActivityCategory(name: 'Cat1')
        assertNotNull actCat.save()

        def foundActCat = ActivityCategory.get(actCat.id)
        foundActCat.name = 'Cat2'
        foundActCat.save()

        def editedActCat = ActivityCategory.get(actCat.id)
        assertEquals 'Cat2', editedActCat.name
    }

    void testSaveThenDelete() {
        def actCat = new ActivityCategory(name: 'Cat1')
        assertNotNull actCat.save()

        def foundActCat = ActivityCategory.get(actCat.id)
        foundActCat.delete()

        assertFalse ActivityCategory.exists(foundActCat.id)
    }

    void testEvilSave() {
        def actCat = new ActivityCategory()

        assertFalse actCat.validate()
        assertTrue actCat.hasErrors()
        def errors = actCat.errors

        assertEquals "nullable",
                errors.getFieldError("name").code
    }

    void testEvilSaveCorrected() {
        def actCat = new ActivityCategory()

        assertFalse actCat.validate()
        assertTrue actCat.hasErrors()
        def errors = actCat.errors

        actCat.name = "Cat3"

        assertTrue(actCat.validate())
        assertFalse(actCat.hasErrors())
    }
}

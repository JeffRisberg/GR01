import grails.util.Environment

import com.incra.ActivityCategory
import com.incra.ActivityType
import com.incra.Profile
import com.incra.UnitOfMeasure
import com.incra.User
import com.incra.UserGroup


/**
 * The <i>BootStrap</i> class has been extended to set up a few sample users, the UnitOfMeasure table,
 * and some Activity Categories and their Activities.
 * 
 * @author Jeffrey Risberg
 * @since 09/19/10
 */
class BootStrap {

    def init = { servletContext ->
        switch (Environment.current) {
            case Environment.DEVELOPMENT:
            case Environment.TEST:
            case Environment.PRODUCTION:

                createBasicUsersIfRequired()
                createBasicUserGroupsIfRequired()
                createUnitOfMeasuresIfRequired()
                createActivityCategoriesIfRequired()
                break;
        }
    }

    def destroy = {
    }

    void createBasicUsersIfRequired() {
        if (User.count() == 0) {
            println "Fresh Database. Creating basic users."
            Profile profile

            profile = new Profile(fullName : "admin", email: "admin@yourhost.com", country: "USA", timeZone: "PST")
            new User(userId: "admin", profile: profile, password: "adminpassword").save()

            profile = new Profile(fullName : "Vive Sloth", email: "sloth@yourhost.com", country: "AUS")
            new User(userId: "sloth", profile: profile, password: "slothpassword").save()

            profile = new Profile(fullName : "Bob Jones", email: "bjones@yourhost.com", country: "USA", timeZone: "EST")
            new User(userId: "bob", profile: profile, password: "bobpassword").save()

            profile = new Profile(fullName : "Sally Smith", email: "sally@yourhost.com", country: "JPN")
            new User(userId: "sally", profile: profile, password: "sallypassword").save()
        } else {
            println "Existing users, skipping creation."
        }
    }

    void createBasicUserGroupsIfRequired() {
        if (UserGroup.count() == 0) {
            println "Fresh Database. Creating basic user groups."

            new UserGroup(name: "Company1").save()
            new UserGroup(name: "Company2").save()
            new UserGroup(name: "Company3").save()
        } else {
            println "Existing user groups, skipping creation."
        }
    }

    void createUnitOfMeasuresIfRequired() {
        if (UnitOfMeasure.count() == 0) {
            println "Fresh Database. Creating units of measure."

            UnitOfMeasure uom

            uom = new UnitOfMeasure(id: UnitOfMeasure.UOM_Minutes, name: "minutes");
            uom.save();
            uom = new UnitOfMeasure(id: UnitOfMeasure.UOM_Counts, name: "counts");
            uom.save()
            uom = new UnitOfMeasure(in: UnitOfMeasure.UOM_Calories, name: "calories")
            uom.save();
        }
        else {
            println "Existing uom values, skipping creation."
        }
    }

    void createActivityCategoriesIfRequired() {
        if (ActivityCategory.count() == 0) {
            println "Fresh Database. Creating activity categories."

            UnitOfMeasure uomMinutes = UnitOfMeasure.get(UnitOfMeasure.UOM_Minutes)
            UnitOfMeasure uomCounts = UnitOfMeasure.get(UnitOfMeasure.UOM_Counts)
            UnitOfMeasure uomCalories = UnitOfMeasure.get(UnitOfMeasure.UOM_Calories)
            ActivityCategory activityCategory
            ActivityType activityType

            activityCategory = new ActivityCategory(id: ActivityCategory.AC_Physical, name: "Physical Activities")
            activityCategory.addToActivityTypes(new ActivityType(activityCategory: activityCategory, name: "Treadmill", unitOfMeasure: uomMinutes))
            activityCategory.addToActivityTypes(new ActivityType(activityCategory: activityCategory, name: "Elliptical", unitOfMeasure: uomMinutes))
            activityCategory.save();

            activityCategory = new ActivityCategory(id: ActivityCategory.AC_Wellness, name: "Wellness")
            activityCategory.addToActivityTypes(new ActivityType(activityCategory: activityCategory, name: "Study Break", unitOfMeasure: uomCounts))
            activityCategory.addToActivityTypes(new ActivityType(activityCategory: activityCategory, name: "Practice Instrument", unitOfMeasure: uomMinutes))
            activityCategory.addToActivityTypes(new ActivityType(activityCategory: activityCategory, name: "Sleep", unitOfMeasure: uomMinutes))
            activityCategory.save()

            activityCategory = new ActivityCategory(id: ActivityCategory.AC_FoodAndDrink, name: "Food and Drink")
            activityCategory.addToActivityTypes(new ActivityType(activityCategory: activityCategory, name: "Limit Snacks", unitOfMeasure: uomCalories))
            activityCategory.addToActivityTypes(new ActivityType(activityCategory: activityCategory, name: "Cook Dinner", unitOfMeasure: uomCounts))
            activityCategory.save()
        } else {
            println "Existing activity categories, skipping creation."
        }
    }
}

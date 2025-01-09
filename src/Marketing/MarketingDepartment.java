package src.Marketing;

import src.App;

import src.Marketing.src.*;
import src.Marketing.src.interfaces.ICollab;
import src.Marketing.src.interfaces.ICollabMember;
import src.Modeling.src.Event;
import src.Security.src.SecurityRequestScheduler;

import java.util.ArrayList;
import java.util.Scanner;

public class MarketingDepartment {
    public static HOD hod;
    public static FileManager fileManager;
    Scanner s = new Scanner(System.in);

    public MarketingDepartment() {
        fileManager = new FileManager();
        hod = fileManager.getHOD();
    }

    public void start() throws Exception {
        int login = 0;
        while (login != 3) {
            System.out.println("""
                    
                    Which User are you Logging in as?
                     1: Head of Department
                     2: Admin
                     3: Exit Marketing Department
                    """);
            login = s.nextInt();
            s.nextLine();
            switch (login) {
                case 1 -> initiateHOD();
                case 2 -> initiateAdmin();
            }
        }
        App.prompt();
    }

    private void initiateHOD() {
        int c = 0;

        while (c != 6) {
            System.out.println("""
                    
                    Please choose an action you want to take:
                     1: advertiseEven
                     2: advertiseDesign
                     3: getAllEventAdvertisements
                     4: getAllDesignAdvertisements
                     5: manageCollaborations
                     6: Back
                     111: Create Security Schedule
                     112: Show All Security Requests
                     113: Delete Security Request By ID
                    """);
            c = s.nextInt();
            switch (c) {
                case 1 -> {
                    ArrayList<Integer> eventIds = new ArrayList<>();
                    for(Event event: App.modelingDepartment.getEvents()) {
                        eventIds.add(event.getId());
                    }
                    System.out.println("Which Event: " + eventIds);
                    int event = s.nextInt();

                    AdvertType type = AdvertType.COMMERCIAL;
                    System.out.println("""
                            What type of Advertisement?
                             1: Billboard
                             2: Magazine
                             3: Social Media
                             4: Commercial""");
                    int x = s.nextInt();
                    switch (x) {
                        case 1 -> type = AdvertType.BILLBOARD;
                        case 2 -> type = AdvertType.MAGAZINE;
                        case 3 -> type = AdvertType.SOCIALMEDIA;
                    }
                    EventAdvertisement e = hod.createEventAdvert(App.modelingDepartment.getEvent(event), type);
                    System.out.println(e.toString());
                    fileManager.addEventAdvert(e);
                }
                case 2 -> {
                    AdvertType type = AdvertType.COMMERCIAL;
                    System.out.println("""
                            What type of Advertisement?
                             1: Billboard
                             2: Magazine
                             3: Social Media
                             4: Commercial""");
                    int x = s.nextInt();
                    switch (x) {
                        case 1 -> type = AdvertType.BILLBOARD;
                        case 2 -> type = AdvertType.MAGAZINE;
                        case 3 -> type = AdvertType.SOCIALMEDIA;
                    }
                    s.nextLine();
                    System.out.println("Any Special Notes? You will be advertising a ball gown");
                    String str = s.nextLine();
                    DesignAdvertisement e = hod.createDesignAdvert(type, str);
                    System.out.println(e.toString());
                    fileManager.addDesignAdvert(e);
                }
                case 3 -> {
                    for(EventAdvertisement ad: hod.getEventAdverts()) {
                        System.out.println(ad.toString());
                    }
                    System.out.println();
                }
                case 4 -> {
                    for(DesignAdvertisement ad: hod.getDesignAdverts()) {
                        System.out.println(ad.toString());
                    }
                    System.out.println();
                }
                case 5 -> initiateManagement();

                case 111 -> {
                    SecurityRequestScheduler scheduler = new SecurityRequestScheduler();
                    scheduler.addSecurityRequest();
                }
                case 112 -> {
                    SecurityRequestScheduler scheduler1 = new SecurityRequestScheduler();
                    scheduler1.showAllSecurityRequests();
                }
                case 113 -> {
                    SecurityRequestScheduler scheduler2 = new SecurityRequestScheduler();
                    scheduler2.deleteScheduleByID();
                }
            }
        }
    }

    private void initiateAdmin() {
        Scanner s = new Scanner(System.in);
        int c = 0;

        while (c != 2) {
            System.out.println("""
                    Please choose an action you want to take:\s
                     1: manage HOD
                     2: Back""");
            c = s.nextInt();
            if (c == 1) {
                int response = 0;
                while (response != 2) {
                    System.out.println("""
                            Managing HOD:\s
                             1: add
                             2: Back""");
                    response = s.nextInt();
                    if (response == 1) {
                        src.Modeling.src.HOD hod = new src.Modeling.src.HOD();
                        System.out.println(hod);
                    }
                }
            }
        }
    }

    private void initiateManagement() {
        int userChoice = 0;
        while (userChoice != 3) {
            System.out.println("""
                    
                    Please choose an action you want to take:
                     1: manageApprovalList
                     2: manageCollaborations
                     3: Back
                    """);
            userChoice = s.nextInt();
            s.nextLine();
            switch (userChoice) {
                case 1 -> {
                    int choice = 0;
                    while (choice != 5) {
                        System.out.println("""
                            
                            Please choose an action you want to take:
                             1: addApprovedMember
                             2: updateApprovedMember
                             3: deleteApprovedMember
                             4: printApprovedMembers
                             5: Back
                            """);
                        choice = s.nextInt();
                        s.nextLine();

                        switch (choice) {
                            case 1 -> {
                                System.out.println("""
                                        
                                        What kind of member would you like to approve?
                                         1: Celebrity
                                         2: Brand""");
                                int type = s.nextInt();
                                s.nextLine();

                                System.out.println("""
                                        
                                        What is the name of the Celebrity/Brand you will like to approve?""");
                                String name = s.nextLine();

                                if(!fileManager.checkMemberExistence(name)) {
                                    ICollabMember member;

                                    if(type == 1) {
                                        member = new Celebrity(name);
                                    } else {
                                        member = new Brand(name);
                                    }
                                    
                                    System.out.println(member);
                                    System.out.println("\nAre you sure you want to approve this Celeb/Brand?(y/n)");

                                    if(s.nextLine().equalsIgnoreCase("y")) {
                                        System.out.println("\nSending Approval to System...");
                                        hod.addApprovedCollab(member);
                                    } else {
                                        System.out.println("\nCanceling approval...");
                                    }
                                } else {
                                    System.out.println("\nCelebrity/Brand already in approval list, aborting task...");
                                }
                            }
                            case 2 -> {
                                System.out.println("""
                                        
                                        Which Celebrity/Brand would you like to change?(full name)""");
                                hod.printApprovedMembers();
                                String name = s.nextLine();

                                ICollabMember member = hod.getMember(name);

                                System.out.println(member);
                                System.out.println("\nWhat is the new name of the celebrity");
                                String newName = s.nextLine();

                                if(!fileManager.checkMemberExistence(newName)) {
                                    System.out.println("Is this correct: " + newName + "?(y/n)");
                                    if(s.nextLine().equalsIgnoreCase("y")) {
                                        System.out.println("Updating member...");
                                        member.changeName(newName);
                                        hod.updateMember(member);
                                        System.out.println(member);
                                    } else {
                                        System.out.println("Aborting change...");
                                    }
                                } else {
                                    System.out.println("Name already exists in the system, aborting change...");
                                }
                            }
                            case 3 -> {
                                System.out.println("\nWhich member would you like to remove?(full name)");
                                hod.printApprovedMembers();

                                String name = s.nextLine();

                                if(fileManager.checkMemberExistence(name)) {
                                    ICollabMember member = hod.getMember(name);

                                    System.out.println(member);
                                    System.out.println("\nAre you sure you want to remove " + member.getName() + "?(y/n)");

                                    if(s.nextLine().equalsIgnoreCase("y")) {
                                        hod.removeMember(member);
                                    } else {
                                        System.out.println("\n Aborting removal...");
                                    }
                                }
                            }
                            case 4 -> fileManager.printApprovedMembers();
                        }
                    }
                }
                case 2 -> {
                    int choice = 0;
                    while (choice != 5) {
                        System.out.println("""
                                
                                Please choose an action you want to take:
                                 1: addCollaboration
                                 2: removeCollaboration
                                 3: deleteCollaboration
                                 4: printCollaborations
                                 5: Back
                                """);
                        choice = s.nextInt();
                        s.nextLine();

                        switch (choice) {
                            case 1 -> {
                                boolean memberApproval = false;

                                while (!memberApproval) {
                                    System.out.println("\nWhich approved Celebrity/Brand will you like to collab with (full name)");
                                    hod.printApprovedMembers();

                                    String name = s.nextLine();
                                    ICollabMember member = hod.getMember(name);
                                    if(member != null) {
                                        memberApproval = member.requestCollab();
                                        if(memberApproval) {
                                            System.out.println("""
                                                
                                                Which type of collaboration will you do?
                                                 1: Design
                                                 2: Event""");
                                            int adType = s.nextInt();
                                            s.nextLine();

                                            switch (adType) {
                                                case 1 -> {
                                                    ArrayList<DesignAdvertisement> designAds = fileManager.getDesignAdverts();
                                                    System.out.println("\nWhich design ad would you like to collab with?(id)");
                                                    for(DesignAdvertisement ad: designAds) {
                                                        System.out.println("\nID:" + ad.getId() +
                                                                "\n Type:" + ad.getAdvertType() +
                                                                "\n Design: " + ad.getDesign().getDesignName() +
                                                                "\n Notes: " + ad.getNotes());
                                                    }

                                                    DesignAdvertisement ad = fileManager.getDesignAdvertById(s.nextInt());
                                                    s.nextLine();

                                                    System.out.println(ad);
                                                    System.out.println("\nAre you sure you want to add this Celebrity/Brand to this Ad?(y/n)");

                                                    if(s.nextLine().equalsIgnoreCase("y")) {
                                                        Collab collab = fileManager.checkCollabExistence(name);
                                                        if(collab == null) {
                                                            collab = new Collab(member);
                                                        }
                                                        collab.addAdvertisement(true, ad);

                                                        System.out.println(collab);
                                                        hod.addCollab(collab);
                                                    } else {
                                                        System.out.println("Aborting collab request...");
                                                    }
                                                }
                                                case 2 -> {
                                                    ArrayList<EventAdvertisement> eventAds = fileManager.getEventAdverts();
                                                    System.out.println("\nWhich event ad would you like to collab with?(id)");
                                                    for(EventAdvertisement ad: eventAds) {
                                                        System.out.println("\nID:" + ad.getId() +
                                                                "\n Type:" + ad.getAdvertType() +
                                                                "\n Event: " + ad.getEvent().getType());
                                                    }

                                                    EventAdvertisement ad = fileManager.getEventAdvertById(s.nextInt());
                                                    s.nextLine();

                                                    System.out.println(ad);
                                                    System.out.println("\nAre you sure you want to add this Celebrity/Brand to this Ad?(y/n)");

                                                    if(s.nextLine().equalsIgnoreCase("y")) {
                                                        Collab collab = fileManager.checkCollabExistence(name);
                                                        if(collab == null) {
                                                            collab = new Collab(member);
                                                        }
                                                        collab.addAdvertisement(false, ad);

                                                        System.out.println(collab);
                                                        hod.updateCollab(collab);
                                                    } else {
                                                        System.out.println("Aborting collab request...");
                                                    }
                                                }
                                            }

                                        } else {
                                            System.out.println("\n" + member.getName() + " rejected your request");
                                        }
                                    } else {
                                        System.out.println("Celebrity/Brand with the name " + name + " is not found." +
                                                "\n Please get them approved before requesting collaborations.");
                                    }
                                }
                            }
                            case 2 -> {
                                System.out.println("\nWhich Collab would you like to change?(id)");
                                hod.printCollabs();

                                int collabId = s.nextInt();
                                s.nextLine();

                                ICollab collab = fileManager.getCollabById(collabId);

                                System.out.println("""
                                        
                                        Which ad will you remove?
                                         1: Design
                                         2: Event""");

                                switch (s.nextInt()) {
                                    case 1 -> {
                                        System.out.println("\nWhich ad will you remove?");
                                        System.out.println(collab.getAdvertisementIds(true));

                                        DesignAdvertisement designAd = fileManager.getDesignAdvertById(s.nextInt());
                                        collab.removeAdvertisement(true, designAd);

                                        hod.updateCollab(collab);
                                    }
                                    case 2 -> {
                                        System.out.println("\nWhich ad will you remove?");
                                        System.out.println(collab.getAdvertisementIds(false));

                                        EventAdvertisement eventAd = fileManager.getEventAdvertById(s.nextInt());
                                        collab.removeAdvertisement(false, eventAd);

                                        hod.updateCollab(collab);
                                    }
                                }
                                s.nextLine();
                            }
                            case 3 -> {
                                System.out.println("\nWhich Collab would you like to delete?(id)");
                                hod.printCollabs();

                                ICollab collab = fileManager.getCollabById(s.nextInt());
                                s.nextLine();

                                fileManager.removeCollab(collab);
                            }
                            case 4 -> hod.printCollabs();
                        }
                    }
                }
            }
        }
    }
}

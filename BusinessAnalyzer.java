//Name: Anson Lee
//package com.usf.245.a2;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BusinessAnalyzer {
    static ArrayDeque userHistory= new ArrayDeque();//stores user inputs in user interactions
    static boolean first = true;//skip first line

    static void readFile(FileReader fr, List listN, List listZ, String input) throws IOException {//reads each line of file, fr = file in args[0], listN = list for naics, listZ = list for zip codes, input = whether to add values to LinkedList or ArrList
        try {
            BufferedReader read = new BufferedReader(fr);
            String line;

            if(input.equalsIgnoreCase("LL")) {//for LinkedList use
                while ((line = read.readLine()) != null) {
                    if(!first) {
                        String[] values = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");//splits by columns
                        checkLLInfo(values, (LinkedList) listN, (LinkedList) listZ);
                    } else {
                        first = false;
                    }
                }
            } else if(input.equalsIgnoreCase("AL")) {//for ArrList use
                while ((line = read.readLine()) != null) {
                    if(!first) {
                        String[] values = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");//splits by columns
                        checkALInfo(values, (ArrList) listZ,(ArrList) listN);
                    } else {
                        first = false;
                    }
                }
            }

            read.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void checkLLInfo(String[] info, LinkedList listN, LinkedList listZ) {//takes in each line in file, listN = list of naics, listZ = list of zip codes
        if(info[9].trim().equals("")) {//if closed or not
            if(info[14].trim().length() > 0) {//if has a zipcode
                String zipCode = info[14].trim();
                LLNode node = listZ.checkZip(listZ, zipCode);//node with null as zip or zipcode as the one from info array
                boolean foundNAICS = false;

                if(info[17].trim().length() > 0) {//if there is a naics description
                    foundNAICS = true;
                }

                if(node.getZip() == null) { //null zip node
                    LLNode newNode = new LLNode(zipCode);//create a new node

                    if(foundNAICS) {//add naics description if one
                        newNode.searchBusinessType(info[17].trim());
                    }

                    if(info[23].trim().length() > 0) {//add neighborhood if one
                        newNode.searchNeighborhood(info[23].trim());
                    }

                    if(!(info[1].trim().equals(""))) {//add business account number if one
                        newNode.searchBusiness(info[1].trim());
                    }

                    listZ.add(newNode);//add node to list
                } else {//node with zipcode already exists, repeat the same things as above if node had null as zip but no new object created
                    if(foundNAICS) {
                        node.searchBusinessType(info[17].trim());
                    }

                    if(!(info[23].trim().equals(""))) {
                        node.searchNeighborhood(info[23].trim());
                    }

                    if(!(info[1].trim().equals(""))) {
                        node.searchBusiness(info[1].trim());
                    }
                }
            }

            if(info[16].trim().length() > 0) {//if it has a naics range
                String[] range = info[16].trim().split(" ");//split on space for each range

                for(int i=0; i < range.length; i++) {
                    if(range[i].length() > 0) {
                        String[] val = range[i].trim().split("-");//splits into two numbers, probably should have just kept it as it was in hindsight but too late

                        LLNode rangeNode = listN.checkRange(listN, val[0], val[1]);//same as zip but check if the range already exists

                        if(rangeNode.getMin() == null) {//if the min is null in the node
                            LLNode newRangeNode = new LLNode(val[0], val[1]);//create new node

                            if(!info[23].equals("")) {//add neighborhood if one exists
                                newRangeNode.searchNeighborhood(info[23]);
                            }

                            if(!info[7].equals("")) {//add zip code if one exists
                                newRangeNode.searchZip(info[7]);
                            }

                            if(!info[1].equals("")) {//add business account number if one exists
                                newRangeNode.searchBusiness(info[1]);
                            }

                            listN.add(newRangeNode);//add node
                        } else {//same as above, but no new object created
                            if(!info[14].equals("")) {
                                rangeNode.searchZip(info[14]);
                            }

                            if(!info[1].equals("")) {
                                rangeNode.searchBusiness(info[1]);
                            }

                            if(!info[23].equals("")) {
                                rangeNode.searchNeighborhood(info[23]);
                            }
                        }
                    }
                }
            }
            listZ.incBusiness();//increment the amount of businesses

            if(!info[8].equals("")) {//check if its a business started last year
                String[] numbers = info[8].trim().split("/");

                if(numbers[2].equals("2022")) {
                    listZ.incNewBusiness();//increase new business count
                }
            }
        } else {
            listZ.incClosedBusiness();//happens if business is closed, increase closed business count
        }
    }

    static void checkALInfo(String[] info, ArrList listZ, ArrList listN) {//similar to checkLLInfo() since basically doing the same thing, but creating different object with different functions/methods
        if(info[9].trim().equals("")) {
            if(info[14].trim().length() > 0) {
                String zipCode = info[14].trim();
                ArrObject zipObj = listZ.checkZip(zipCode);
                boolean foundNAICS = false;

                if(info[17].trim().length() > 0) {
                    foundNAICS = true;
                }

                if(zipObj.getZip() == null) {
                    ArrObject newZipObj = new ArrObject(zipCode);

                    if(foundNAICS) {
                        newZipObj.addBusinessType(info[17].trim());
                    }

                    if(info[23].trim().length() > 0) {
                        newZipObj.addNeighborhood(info[23].trim());
                    }

                    if(!(info[1].trim().equals(""))) {
                        newZipObj.addBusiness(info[1].trim());
                    }

                    listZ.add(newZipObj);
                } else {
                    if(foundNAICS) {
                        zipObj.addBusinessType(info[17].trim());
                    }

                    if(!(info[23].trim().equals(""))) {
                        zipObj.addNeighborhood(info[23].trim());
                    }

                    if(!(info[1].trim().equals(""))) {
                        zipObj.addBusiness(info[1].trim());
                    }
                }
            }

            if(info[16].trim().length() > 0) {
                String[] range = info[16].trim().split(" ");

                for(int i=0; i < range.length; i++) {
                    if(range[i].length() > 0) {
                        String[] val = range[i].trim().split("-");

                        ArrObject rangeArrObj = listN.checkRange(val[0], val[1]);

                        if(rangeArrObj.getMinNAICS() == null) {
                            ArrObject newRangeArrObj = new ArrObject(val[0], val[1]);

                            if(!info[23].equals("")) {
                                newRangeArrObj.addNeighborhood(info[23]);
                            }

                            if(!info[7].equals("")) {
                                newRangeArrObj.addZipCode(info[7]);
                            }

                            if(!info[1].equals("")) {
                                newRangeArrObj.addBusiness(info[1]);
                            }

                            listN.add(newRangeArrObj);
                        }
                        else {
                            if(!info[14].equals("")) {
                                rangeArrObj.addZipCode(info[14]);
                            }

                            if(!info[1].equals("")) {
                                rangeArrObj.addBusiness(info[1]);
                            }

                            if(!info[23].equals("")) {
                                rangeArrObj.addNeighborhood(info[23]);
                            }
                        }
                    }
                }
            }
            listZ.incBusinessCount();

            if(!info[8].equals("")) {
                String[] numbers = info[8].trim().split("/");

                if(numbers[2].equals("2022")) {
                    listZ.incNewBusinessCount();
                }
            }
        } else {
            listZ.incClosedCount();
        }
    }

    static void LLuserInteraction(LinkedList listN, LinkedList listZ) {//user interaction taking in LinkedLists, listN = list of naics, listZ = list of zip codes
        Scanner scan = new Scanner(System.in);

        System.out.println("Type 'Summary' for overall summary, 'EXIT' to exit program, or search for summary by Zip or NAICS? (For Zip, type 'Zip (5 number zip code) Summary'. For NAICS, type 'NAICS (4 numbers indicating range) Summary'.):");
        while(scan.hasNextLine()) {
            String input = scan.nextLine().trim();//input

            if(input.equalsIgnoreCase("EXIT")) {//exit program
                System.out.println("Exiting.");
                System.exit(0);
            } else if(input.equalsIgnoreCase("Summary")) {//give summary
                userHistory.add(input);
                System.out.println("Total Businesses: " + listZ.getBusiness());
                System.out.println("Closed Businesses: " + listZ.getClosedBusiness());
                System.out.println("New Business in last year: " + listZ.getNewBusiness());
            } else if(input.equalsIgnoreCase("History")) {//give history of user inputs
                while(userHistory.size() > 0) {
                    System.out.println(userHistory.pop());
                }
            } else {
                String[] response = input.trim().split(" ");//splits the input by spaces

                if(response.length != 3) { //check if input is valid
                    System.out.println("Not a valid input.");
                } else {
                    if(response[0].equalsIgnoreCase("Zip") || response[0].equalsIgnoreCase("NAICS")) {
                        try {
                            if(response[0].equalsIgnoreCase("Zip") && response[1].length() == 5 && response[2].equalsIgnoreCase("Summary")) {
                                userHistory.add(input);
                                Iterator<LLNode> itZ = listZ.iterator();//iterator for zip codes
                                boolean found = false;

                                //check first node
                                if(itZ.get().getZip().equals(response[1])) {
                                    System.out.println(itZ.get().getZip() + " Business Summary");
                                    System.out.println("Total Businesses: " + itZ.get().getBusinessCount());
                                    System.out.println("Business Types: " + itZ.get().getBusinessTypeCount());
                                    System.out.println("Neighborhood: " + itZ.get().getNeighborhoodCount());
                                    found = true;
                                }

                                while(!found && itZ.hasNext()) {//check all but first node
                                    itZ.next();
                                    if(itZ.get().getZip().equals(response[1])) {
                                        System.out.println(itZ.get().getZip() + " Business Summary");
                                        System.out.println("Total Businesses: " + itZ.get().getBusinessCount());
                                        System.out.println("Business Types: " + itZ.get().getBusinessTypeCount());
                                        System.out.println("Neighborhood: " + itZ.get().getNeighborhoodCount());
                                        found = true;
                                        break;
                                    }
                                }

                                if(!found) {
                                    System.out.println("Zip code does not exist");
                                }
                            } else if(response[0].equalsIgnoreCase("NAICS") && response[1].length() == 4 && response[2].equalsIgnoreCase("Summary")) {
                                userHistory.add(input);
                                boolean found = false;
                                Iterator<LLNode> itN = listN.iterator();//iterator for naics

                                //check first node
                                if(((Comparable) response[1]).compareTo(itN.get().getMin()) >= 0 && ((Comparable) response[1]).compareTo(itN.get().getMax()) <= 0) {
                                    System.out.println("Total Businesses: " + itN.get().getBusinessCount());
                                    System.out.println("Zip Codes: " + itN.get().getZipCodeCount());
                                    System.out.println("Neighborhood: " + itN.get().getNeighborhoodCount());
                                    found = true;
                                }

                                while(!found && itN.hasNext()) {//check all but first node
                                    itN.next();
                                    if(((Comparable) response[1]).compareTo(itN.get().getMin()) >= 0 && ((Comparable) response[1]).compareTo(itN.get().getMax()) <= 0) {
                                        System.out.println("Total Businesses: " + itN.get().getBusinessCount());
                                        System.out.println("Zip Codes: " + itN.get().getZipCodeCount());
                                        System.out.println("Neighborhood: " + itN.get().getNeighborhoodCount());
                                        found = true;
                                        break;
                                    }
                                }

                                if(!found) {
                                    System.out.println("NAICS does not exist");
                                }
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("Not a valid input.");
                        }
                    } else {
                        System.out.println("Not a valid input.");
                    }
                }
            }
            System.out.println("Type 'Summary' for overall summary, 'EXIT' to exit program, or search for summary by Zip or NAICS? (For Zip, type 'Zip (5 number zip code) Summary'. For NAICS, type 'NAICS (4 numbers indicating range) Summary'.):");
        }
    }

    static void userInteractionAL(ArrList listZ, ArrList listN) {//same as LinkedList one, but takes in ArrList
        Scanner scan = new Scanner(System.in);

        System.out.println("Type 'Summary' for overall summary, 'EXIT' to exit program, or search for summary by Zip or NAICS? (For Zip, type 'Zip (5 number zip code) Summary'. For NAICS, type 'NAICS (4 numbers indicating range) Summary'.):");
        while(scan.hasNextLine()) {
            String input = scan.nextLine().trim();

            if(input.equalsIgnoreCase("EXIT")) {
                System.out.println("Exiting.");
                System.exit(0);
            } else if(input.equalsIgnoreCase("Summary")) {
                userHistory.add(input);
                System.out.println("Total Businesses: " + listZ.getBusinessCount());
                System.out.println("Closed Businesses: " + listZ.getClosedCount());
                System.out.println("New Business in last year: " + listZ.getNewBusinessCount());
            } else if(input.equalsIgnoreCase("History")) {
                while(userHistory.size() > 0) {
                    System.out.println(userHistory.pop());
                }
            } else {
                String[] response = input.trim().split(" ");

                if(response.length != 3) {
                    System.out.println("Not a valid input.");
                } else {
                    if(response[0].equalsIgnoreCase("Zip") || response[0].equalsIgnoreCase("NAICS")) {
                        try {
                            if(response[0].equalsIgnoreCase("Zip") && response[1].length() == 5 && response[2].equalsIgnoreCase("Summary")) {
                                userHistory.add(input);
                                boolean found = false;
                                Iterator<ArrObject> itZ = listZ.iterator();

                                if(itZ.get().getZip().equals(response[1])) { //check first node
                                    System.out.println(itZ.get().getZip() + " Business Summary");
                                    System.out.println("Total Businesses: " + itZ.get().getBusinessSize());
                                    System.out.println("Business Types: " + itZ.get().getBusinessTypeSize());
                                    System.out.println("Neighborhood: " + itZ.get().getNeighborhoodSize());
                                    found = true;
                                }

                                while(!found && itZ.hasNext()) {//check all after first node
                                    if(itZ.next().getZip().equals(response[1])) {
                                        System.out.println(itZ.get().getZip() + " Business Summary");
                                        System.out.println("Total Businesses: " + itZ.get().getBusinessSize());
                                        System.out.println("Business Types: " + itZ.get().getBusinessTypeSize());
                                        System.out.println("Neighborhood: " + itZ.get().getNeighborhoodSize());
                                        found = true;
                                        break;
                                    }
                                }

                                if(!found) {
                                    System.out.println("Zip code does not exist");
                                }
                            } else if(response[0].equalsIgnoreCase("NAICS") && response[1].length() == 4 && response[2].equalsIgnoreCase("Summary")) {
                                userHistory.add(input);
                                boolean found = false;

                                Iterator<ArrObject> itN = listN.iterator();//iterator for naics

                                //check first node
                                if(((Comparable) response[1]).compareTo(itN.get().getMinNAICS()) >= 0 && ((Comparable) response[1]).compareTo(itN.get().getMaxNAICS()) <= 0) {
                                    System.out.println("Total Businesses: " + itN.get().getBusinessSize());
                                    System.out.println("Zip Codes: " + itN.get().getZipCodeSize());
                                    System.out.println("Neighborhood: " + itN.get().getNeighborhoodSize());
                                    found = true;
                                }

                                while(!found && itN.hasNext()) {//check all after first node
                                    if(((Comparable) response[1]).compareTo(itN.get().getMinNAICS()) >= 0 && ((Comparable) response[1]).compareTo(itN.get().getMaxNAICS()) <= 0) {
                                        System.out.println("Total Businesses: " + itN.get().getBusinessSize());
                                        System.out.println("Zip Codes: " + itN.get().getZipCodeSize());
                                        System.out.println("Neighborhood: " + itN.get().getNeighborhoodSize());
                                        found = true;
                                        break;
                                    }
                                    itN.next();
                                }

                                if(!found) {
                                    System.out.println("NAICS does not exist");
                                }
                            } else {
                                System.out.println("Not a valid input.");
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("Not a valid input.");
                        }
                    } else {
                        System.out.println("Not a valid input.");
                    }
                }
            }
            System.out.println("Type 'Summary' for overall summary, 'EXIT' to exit program, or search for summary by Zip or NAICS? (For Zip, type 'Zip (5 number zip code) Summary'. For NAICS, type 'NAICS (4 numbers indicating range) Summary'.):");
        }
    }

    public static void main(String[] args) {
        try {
            if(args.length > 1) {
                if(args[1].equals("LL")) {//for LinkedList
                    LinkedList<LLNode> naicsLL = new LinkedList<>();//list for naics
                    LinkedList<LLNode> zipCodeLL = new LinkedList<>();//list for zipcode
                    FileReader reader = new FileReader(args[0]);//FileReader for the csv
                    readFile(reader, naicsLL, zipCodeLL, "LL");//read file for LL
                    LLuserInteraction(naicsLL, zipCodeLL);//user interaction for LL
                    reader.close();
                } else if(args[1].equals("AL")) {//for ArrList
                    //same as above but instead of LL its AL
                    ArrList<ArrObject> arrListZip = new ArrList<>();
                    ArrList<ArrObject> arrListNAICS = new ArrList<>();
                    FileReader reader = new FileReader(args[0]);
                    readFile(reader, arrListNAICS, arrListZip, "AL");
                    userInteractionAL(arrListZip, arrListNAICS);
                    reader.close();
                } else {
                    System.out.println("ERROR: Invalid Input");
                }
            } else {
                System.out.println("ERROR: Invalid Input");
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: file can not be found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


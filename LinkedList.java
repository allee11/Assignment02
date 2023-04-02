public class LinkedList<T> implements List<T> {
    LLNode<T> head;//head
    private int business;//count for amount of businesses
    private int closedBusiness;//count for amount of closed businesses
    private int newBusiness;//count for amount of new businesses
    private int size;//size of list, probably not needed

    LinkedList() {
        head = null;
    }

    @Override
    public T get(int pos) {//do not think its useful, but gets node at position asked, pos = position in the list
        try {
            if(head == null || pos >= size) {
                throw new Exception();
            } else {
                LLNode searcher = head;

                while(pos > 0) {
                    searcher = searcher.next;
                    pos = pos-1;
                }

                return (T) searcher;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(T node) {//adds node to the list, LLNode with zip or naics range, node = LLNode to add to list
        if(head == null) {
            head = (LLNode) node;
        } else {
            ((LLNode) node).next = head;
            head = (LLNode) node;
        }
        size++;//increase size of list
        return true;
    }

    LLNode checkZip(LinkedList<T> list, T zip) {//checks if the zip code already exists, returns LLNode with null if not and returns node with same zip code asked if found
        //list = this list, probably should have used this.head instead of adding list to parameter, zip = zip code
        if(list.head == null) {
            return new LLNode();
        }

        LLNode checker = list.head;
        while(checker != null) {
            if(checker.getZip().equals(zip)) {
                return checker;
            }
            checker = checker.next;
        }
        return new LLNode();
    }

    LLNode checkRange(LinkedList<T> list, T min, T max) {//same as checkZip() but with the naics range, min = smaller naics value, max = bigger naics value
        if(list.head == null) {
            return new LLNode();
        }

        LLNode checker = list.head;
        while(checker != null) {
            if(checker.getMin().equals(min) && checker.getMax().equals(max)) {
                return checker;
            }
            checker = checker.next;
        }
        return new LLNode();
    }

    public void incBusiness() {//increase business count
        business++;
    }

    public void incClosedBusiness() {//increase closed business count
        closedBusiness++;
    }

    public void incNewBusiness() {//increase new business count
        newBusiness++;
    }

    int getBusiness() {//return business count as int
        return business;
    }

    int getClosedBusiness() {//return closed business count as int
        return closedBusiness;
    }

    int getNewBusiness() {//return new business count as int
        return newBusiness;
    }

    public int size() {//return size of list as int
        return size;
    }

    private class LinkedListIterator<T> implements Iterator<T> {//same as one in slides but added get()
        private LLNode node = head;

        public boolean hasNext() {
            return node.next != null;
        }

        public T next() { //return data and advance
            LLNode prev = node;
            node = node.next;
            return (T) prev;
        }

        public T get() {//get node
            return (T)node;
        }
    }

    public Iterator<T> iterator() {//returns this list's iterator
        return new LinkedListIterator<>();
    }
}

class LLNode<T> {//messy, should be separated into two different nodes, but is dual purpose
    ZipCodeList<T>  businessTypeList = new ZipCodeList<>(); //for zip code list use, holds business types
    NAICSList<T> zipList = new NAICSList<>();// for NAICS list use, holds zip codes
    BusinessList<T> businessAccList = new BusinessList<>();//holds business numbers
    NeighborhoodList<T> neighborhoodList = new NeighborhoodList<>();//holds neighborhoods
    LLNode<T> next;//next
    private T zip; //stores zipcodes
    private T minNAICS;//stores smaller value of the NAICS range
    private T maxNAICS;//stores bigger value of the NAICS range
    private int neighborhoodCount;//amount of unique neighborhoods
    private int businessCount;//amount of unique business numbers
    private int businessTypeCount;//amount of unique business types
    private int zipCodeCount;//amount of unique zip codes

    LLNode() {//new node
        zip = null;
        minNAICS = null;
    }

    LLNode(T min, T max) {//node with NAICS range, min = smaller naics value, max = bigger naics value
        minNAICS = min;
        maxNAICS = max;
        next = null;
    }

    LLNode(T code) {// node with zip code, code = zip code
        zip = code;
        next = null;
    }

    T getZip() {
        return zip;
    }//return zip code

    T getMin() {
        return minNAICS;
    }//return smaller naics number

    T getMax() {
        return maxNAICS;
    }//return bigger naics number

    int getNeighborhoodCount() {
        return neighborhoodCount;
    }//return number of unique neighborhoods as int

    int getBusinessCount() {
        return businessCount;
    }//return number of unique business numbers as int

    int getBusinessTypeCount() {
        return businessTypeCount;
    }//return number of unique business types as int

    int getZipCodeCount() {
        return zipCodeCount;
    }//return number of unique zip codes as int

    LLNode searchBusiness(T accNum) {//add business number to the business number list if it is unique and return itself, accNum = business account number
        if(businessAccList.head == null) {
            BusinessList.BusinessNode node = new BusinessList.BusinessNode(accNum);
            businessAccList.addBusiness(node);
            businessCount += 1;
            return this;
        } else {
            BusinessList.BusinessNode checker = businessAccList.head;
            int count = 0;

            while(checker != null) {
                if(checker.getBusAccNum().equals(accNum)) {
                    count++;
                    break;
                }
                checker = checker.next;
            }

            if(count == 0) {
                BusinessList.BusinessNode node = new BusinessList.BusinessNode(accNum);
                businessAccList.addBusiness(node);
                businessCount += 1;
                return this;
            }
            return this;
        }
    }

    LLNode searchNeighborhood(T neighborhood) {//add neighborhood to the neighborhood list if it is unique and return itself, neighborhood = neighborhood boundaries
        if(neighborhoodList.head == null) {
            NeighborhoodList.NeighborhoodNode node = new NeighborhoodList.NeighborhoodNode(neighborhood);
            neighborhoodList.addNeighborhood(node);
            neighborhoodCount += 1;
            return this;
        } else {
            NeighborhoodList.NeighborhoodNode checker = neighborhoodList.head;
            int count = 0;

            while(checker != null) {
                if(checker.getNeighborhood().equals(neighborhood)) {
                    count++;
                    break;
                }
                checker = checker.next;
            }

            if(count == 0) {
                NeighborhoodList.NeighborhoodNode node = new NeighborhoodList.NeighborhoodNode(neighborhood);
                neighborhoodList.addNeighborhood(node);
                neighborhoodCount += 1;
                return this;
            }
            return this;
        }
    }

    LLNode searchBusinessType(T description){//add business type to the business type list if it is unique and return itself, only used by zip code list, description = naics description
        if(businessTypeList.head == null) {
            ZipCodeList.BusinessTypeNode node = new ZipCodeList.BusinessTypeNode(description);
            businessTypeList.addBusinessType(node);
            businessTypeCount += 1;
            return this;
        } else {
            ZipCodeList.BusinessTypeNode checker = businessTypeList.head;
            int count = 0;

            while(checker != null) {
                if(checker.getBusinessDescription().equals(description)) {
                    count++;
                    break;
                }
                checker = checker.next;
            }

            if(count == 0) {
                ZipCodeList.BusinessTypeNode node = new ZipCodeList.BusinessTypeNode(description);
                businessTypeList.addBusinessType(node);
                businessTypeCount += 1;
                return this;
            }
            return this;
        }
    }

    LLNode searchZip(T code) {//add zip code to the zip code list if it is unique and return itself, only used by naics list, code = zip code
        if(zipList.head == null) {
            NAICSList.ZipNode zipNode = new NAICSList.ZipNode(code);
            zipCodeCount += 1;
            zipList.addZipCode(zipNode);
            return this;
        } else {
            NAICSList.ZipNode checker = zipList.head;
            int count = 0;

            while(checker != null) {
                if(checker.getZip().equals(code)) {
                    count++;
                    break;
                }
                checker = checker.next;
            }

            if(count == 0) {
                NAICSList.ZipNode node = new NAICSList.ZipNode(code);
                zipList.addZipCode(node);
                zipCodeCount += 1;
                return this;
            }
            return this;
        }
    }
}

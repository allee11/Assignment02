//has a lot of similarities to LinkedList that I do not want to explain
public class ArrList<T> implements List<T> {
    private T[] arr;//arr for each object
    private int size;//amount of objects in arr
    private int business;//count for businesses
    private int closed;//count for closed businesses
    private int newBusiness;//count for new businesses


    ArrList() {//new list
        arr = (T[]) new Object[500];
        size = 0;
        business = 0;
        closed = 0;
    }

    public T get(int pos) {//returns object at the position, pos = position
        if (pos < 0 || pos >= size) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid position");
            }
        }
        return arr[pos];
    }

    public int size() {
        return size;
    }//returns amount of objects in array as int

    public void incBusinessCount() {
        business++;
    }//increase business count

    public void incClosedCount() {
        closed++;
    }//increase closed business count

    public void incNewBusinessCount() {
        newBusiness++;
    }//increase new business count

    public int getBusinessCount() {
        return business;
    }//return business count as int

    public int getClosedCount() {
        return closed;
    }//return closed business count as int

    public int getNewBusinessCount() {
        return newBusiness;
    }//return new business count as int

    public boolean add(T item) {//add object to the arr if unique, item = object to be added
        for(int i=0; i < size(); i++) {
            if(arr[i].equals(item)) {
                return false;
            }
        }

        if (size == arr. length) {//avoid ArrayIndexOutOfBounds
            growArray();
        }

        arr[size++] = item;//add item and increase size
        return true;
    }

    ArrObject checkZip(T zip) {//same as linked list but with objects, zip = zip code
        for(int i=0; i < size(); i++) {
            if(((ArrObject) arr[i]).getZip().equals(zip)) {
                return (ArrObject) arr[i];
            }
        }
        return new ArrObject();
    }

    ArrObject checkRange(T min, T max) {//same as linked list but with objects, min = smaller naics value, max = bigger naics value
        for(int i=0; i < size(); i++) {
            if(((ArrObject) arr[i]).getMinNAICS().equals(min) && ((ArrObject) arr[i]).getMaxNAICS().equals(max)) {
                return ((ArrObject) arr[i]);
            }
        }
        return new ArrObject();
    }

    private void growArray() {//increase array size of needed
        T [] newArr = (T[]) new Object[arr.length * 2];//new array with twice the size

        for (int i = 0; i < arr.length; i++) {//insert back the values
            newArr[i] = arr[i];
        }
        arr = newArr;//set arr as this new arr
    }

    private class ArrListIterator<T> implements Iterator<T> {//iterator for the list
        private int nextIndex = 0;

        public boolean hasNext() {
            return nextIndex < size && nextIndex >= 0;
        }

        public T next() {
            return (T) arr[nextIndex++];
        }

        public T get() {
            return (T) arr[nextIndex];
        }
    }

    public Iterator<T> iterator() {//return the iterator
        return new ArrListIterator<>();
    }
}

class ArrObject<T> {//messy since very similar to LLNode in LinkedList, could be split in two, object for ArrList
    private T zip;//zip code
    private T minNAICS;// smaller naics value
    private T maxNAICS;//bigger naics value
    private T[] neighborhoodArr;//stores unique neighborhoods
    private T[] businessArr;//stores unique business numbers
    private T[] zipCodeArr;//stores unique zip codes
    private T[] businessTypeArr;//stores unique neighborhoods
    private int neighborhoodSize;//items in neighborhood array
    private int businessSize;//items in business number array
    private int zipCodeSize;//items in zip code array
    private int businessTypeSize;//items in business type array



    ArrObject() {//new null object
        zip = null;
        minNAICS = null;
    }

    ArrObject(T code) {//object with zip code, code = zip code
        neighborhoodArr = (T[]) new Object[50];
        businessArr = (T[]) new Object[1000];
        businessTypeArr = (T[]) new Object[50];
        neighborhoodSize = 0;
        businessSize = 0;
        businessTypeSize = 0;
        zip = code;
    }

    ArrObject(T min, T max) {//object with naics range, min = smaller naics value, max = bigger naics value
        neighborhoodArr = (T[]) new Object[50];
        businessArr = (T[]) new Object[1000];
        zipCodeArr = (T[]) new Object[50];
        neighborhoodSize = 0;
        businessSize = 0;
        zipCodeSize = 0;
        minNAICS = min;
        maxNAICS = max;
    }

    private void growNeighborhoodArray() {//increase size of neighborhood array if needed
        T [] newArr = (T[]) new Object[neighborhoodArr.length * 2];

        for (int i = 0; i < neighborhoodArr.length; i++) {
            newArr[i] = neighborhoodArr[i];
        }
        neighborhoodArr = newArr;
    }

    private void growBusinessArray() {//same as above, but for business numbers array
        T [] newArr = (T[]) new Object[businessArr.length * 2];

        for (int i = 0; i < businessArr.length; i++) {
            newArr[i] = businessArr[i];
        }
        businessArr = newArr;
    }

    private void growZipCodeArray() {//same as above, but for zip code array
        T [] newArr = (T[]) new Object[zipCodeArr.length * 2];

        for (int i = 0; i < zipCodeArr.length; i++) {
            newArr[i] = zipCodeArr[i];
        }
        zipCodeArr = newArr;
    }

    private void growBusinessTypeArray() {//same as above, but for business type array
        T [] newArr = (T[]) new Object[businessTypeArr.length * 2];

        for (int i = 0; i < businessTypeArr.length; i++) {
            newArr[i] = businessTypeArr[i];
        }
        businessTypeArr = newArr;
    }

    public boolean addNeighborhood(T item) {//add neighborhood to neighborhood if unique, item = neighborhood
        for(int i=0; i < getNeighborhoodSize(); i++) {
            if(neighborhoodArr[i].equals(item)) {
                return false;
            }
        }

        if (neighborhoodSize == neighborhoodArr. length) {
            growNeighborhoodArray();
        }

        neighborhoodArr[neighborhoodSize++] = item;//adds neighborhood and increase size
        return true;
    }

    public boolean addBusiness(T item) {//everything same as above, but business number instead of neighborhood
        for(int i=0; i < getBusinessSize(); i++) {
            if(businessArr[i].equals(item)) {
                return false;
            }
        }

        if (businessSize == businessArr. length) {
            growBusinessArray();
        }

        businessArr[businessSize++] = item;
        return true;
    }

    public boolean addZipCode(T item) {//everything same as above, but zip code instead of business number
        for(int i=0; i < getZipCodeSize(); i++) {
            if(zipCodeArr[i].equals(item)) {
                return false;
            }
        }

        if (zipCodeSize == zipCodeArr. length) {
            growZipCodeArray();
        }

        zipCodeArr[zipCodeSize++] = item;
        return true;
    }

    public boolean addBusinessType(T item) {//everything same as above, but business type instead of zip code
        for(int i=0; i < getBusinessTypeSize(); i++) {
            if(businessTypeArr[i].equals(item)) {
                return false;
            }
        }

        if (businessTypeSize == businessTypeArr.length) {
            growBusinessTypeArray();
        }

        businessTypeArr[businessTypeSize++] = item;
        return true;
    }

    public T getZip() {
        return zip;
    }//return zip code

    public T getMinNAICS() {
        return minNAICS;
    }//return smaller naics value

    public T getMaxNAICS() {
        return maxNAICS;
    }//return bigger naics value

    public int getNeighborhoodSize() {
        return neighborhoodSize;
    }//return size of neighborhood array as int

    public int getBusinessSize() {
        return businessSize;
    }//return size of business number array as int

    public int getZipCodeSize() {
        return zipCodeSize;
    }//return size of zip code array as int

    public int getBusinessTypeSize() {
        return businessTypeSize;
    }//return size of business type array as int
}

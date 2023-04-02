public class BusinessList<T> {//list for business account numbers
    BusinessNode<T> head;//head

    static class BusinessNode<T> {//nodes for the list
        BusinessNode<T> next;//next
        private T busAccNum;//number

        BusinessNode() {
        }

        BusinessNode(T accNum) {//node with business number
            busAccNum = accNum;
            next = null;
        }

        T getBusAccNum() {
            return busAccNum;
        }//returns number
    }

    BusinessList() {
        head = null;
    }

    void addBusiness(BusinessNode node) {//add business number
        if(head == null) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
    }
}

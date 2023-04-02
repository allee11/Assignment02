public class ZipCodeList<T> {//list for business types or NAICS description, named poorly, should say BusinessTypeList
    BusinessTypeNode<T> head;//head

    static class BusinessTypeNode<T> {//nodes for this list
        BusinessTypeNode<T> next;//next
        private T businessDescription;//description

        BusinessTypeNode() {
        }

        BusinessTypeNode(T description) {//node of description
            businessDescription = description;
            next = null;
        }

        T getBusinessDescription() {
            return businessDescription;
        }//return description
    }

    ZipCodeList() {
        head = null;
    }

    void addBusinessType(BusinessTypeNode node) {//add node to the list
        if(head == null) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
    }
}


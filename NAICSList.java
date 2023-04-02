public class NAICSList<T> {//list for zip codes, named poorly, should be ZipCodeList or something like that
    ZipNode<T> head;//head

    static class ZipNode<T> {//nodes for the list
        private T zip;//zip code
        ZipNode<T> next;//next

        ZipNode() {
        }

        ZipNode(T code) {//node of zip code
            zip = code;
            next = null;
        }

        T getZip() {
            return zip;
        }//return zip code
    }

    void addZipCode(ZipNode node){//add node to the list
        if(head == null) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
    }
}



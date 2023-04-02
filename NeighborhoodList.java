public class NeighborhoodList<T> {//list for neighborhoods
    NeighborhoodNode<T> head;//head

    static class NeighborhoodNode<T> {//nodes for this list
        private T neighborhood;//neighborhood
        NeighborhoodNode<T> next;//next

        NeighborhoodNode() {
        }

        NeighborhoodNode(T name) {//node with neighborhood
            neighborhood = name;
            next = null;
        }

        T getNeighborhood() {
            return neighborhood;
        }//returns neighborhood
    }

    NeighborhoodList() {
        head = null;
    }

    void addNeighborhood(NeighborhoodNode node) {//add neighborhood to list
        if(head == null) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
    }
}


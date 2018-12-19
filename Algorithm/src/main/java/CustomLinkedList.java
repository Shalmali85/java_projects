public class CustomLinkedList {

    public Node head;
    public Node temp;
    public Node tail;

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public void addItem(int item){
        temp = head;
        if(head == null){
            head = new Node(item);
        }
        else{
            while(true){
                if(head.next == null) {
                    head.next = new Node(item);
                    break;
                }
                    head = head.next;
            }
            head = temp;
        }
    }

    public void printItem(){
        System.out.print("items-->");
        while(head!=null){
            if(head.next!=null) {
                System.out.print(head.item + "->");
            }
            else{
                System.out.print(head.item);
            }
            head = head.next;
        }
        head = temp;

    }

    public Node getLinkedList(){
        return head;
    }


    public Node reverseLinkList(Node current){
        Node next =null;
        Node previous = null;
        while(current!=null) {
           next = current.next;
           current.next = previous;
           previous = current;
           current = next;
        }
        head = previous;
        return head;
    }

    public void remove(int item){
        Node entry = head;
        Node prev = null;
        Node current = entry;
        while(current!=null){
            if(current.item==item && current.next == null){
                prev.next= null;
                break;
            }
            if(current.item==item && current.next != null){
                if(prev!=null) {
                    prev.next = current.next;
                    break;
                }
                else{
                   head= current.next;
                   break;
                }
            }
            prev= current;
            current = current.next;
        }

    }
     static class Node {

      public int item;
      public Node next;

      public Node(int item){
          this.item= item;

      }

        public int getItem() {
            return item;
        }

        public void setItem(int item) {
            this.item = item;
        }
    }


    public static void main(String args[]){
        CustomLinkedList customLinkedList = new CustomLinkedList();
        customLinkedList.addItem(2);
        customLinkedList.addItem(3);
        customLinkedList.addItem(5);
        customLinkedList.addItem(7);
        customLinkedList.addItem(1);
        customLinkedList.printItem();
        customLinkedList.remove(7);
        customLinkedList.reverseLinkList(customLinkedList.getLinkedList());
        customLinkedList.printItem();


    }
}

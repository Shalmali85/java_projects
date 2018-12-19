import java.util.HashMap;
import java.util.LinkedHashMap;

public class LinkedCustomMap<K,V> extends CustomMap<K,V> {

    private transient Entry<K,V> tail,head;
    public void put(K key, V value){
      super.put(key,value);
        Entry<K,V> linkedNode=   new Entry<K,V>(key,value,null);
      addNextNode(linkedNode);
    }
    public void remove(K key){
        super.remove(key);
        maintainOrderOnDeletion(key);
    }

    private void addNextNode(Entry<K,V>linkedNode){
        Entry<K,V> last = tail;
        tail = linkedNode;
       if(last == null){
           head = linkedNode;
       }
       else{
           linkedNode.before= last;
           last.after= linkedNode;
       }
    }

    private void maintainOrderOnDeletion(K key){
        Entry<K,V> entry = head;

       if(key.equals(head.key)) {
           head = (Entry<K, V>) head.after;
       }
       else{
           Entry<K,V> current = entry;
           Entry<K,V> forward = null;
          while(current!=null){
           if(current.key.equals(key) && current.after==null){
             forward.after= null;
             break;
           }
              if(current.key.equals(key) && current.after!=null){
                  forward.after= current.after;
                  break;
              }
           forward= current;
           current = (Entry<K, V>) current.after;

          }
       }
    }
    public void printOrder(){
       while(head!=null) {
           System.out.println("items->"+head.key);
           head = (Entry<K, V>) head.after;
       }
    }

    static class  Entry<K,V> extends CustomMap.Node<K,V>{
       private CustomMap.Node before;
       private CustomMap.Node after;

       public Entry(K key, V value , CustomMap.Node<K,V> next){
          super(key,value,next);
       }
    }

public static void main (String ...args) {
    LinkedCustomMap map = new LinkedCustomMap();
    map.put("apple",2);
    map.put("peach",12);
    map.put("orange",3);
    map.put("mango",4);
    map.put("pears",40);
    map.put("pineapple",5);
    System.out.println("Get value of orange ="+ map.get("orange"));
    System.out.println("Get value of mango ="+ map.get("mango"));
    System.out.println("Get value of apple ="+ map.get("apple"));
    System.out.println("Get value of pears ="+ map.get("pears"));
    System.out.println("Get value of pineapple ="+ map.get("pineapple"));
    System.out.println("Get value of peach ="+ map.get("peach"));
    map.remove("pears");
    System.out.println("Get value of pears ="+ map.get("pears"));
    System.out.println("Get value of mango ="+ map.get("mango"));
    System.out.println("Get value of apple ="+ map.get("apple"));
    System.out.println("Get value of peach ="+ map.get("peach"));
    System.out.println("Get value of orange ="+ map.get("orange"));
    map.printOrder();


}

}


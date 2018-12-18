import java.util.HashMap;
import java.util.LinkedHashMap;

public class LinkedCustomMap<K,V> extends CustomMap<K,V> {

    private transient Entry<K,V> tail,head;
    public void put(K key, V value){
      super.put(key,value);
        Entry<K,V> linkedNode=   new Entry<K,V>(key,value,null);
      addNextNode(linkedNode);
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
    map.put("apple", 2);
    map.put("orange", 3);
    map.put("mango", 4);
    map.put("pears", 40);
    map.put("pineapple", 5);
    map.printOrder();
    System.out.println("Get value of orange =" + map.get("orange"));
    System.out.println("Get value of mango =" + map.get("mango"));
    System.out.println("Get value of apple =" + map.get("apple"));
    System.out.println("Get value of pears =" + map.get("pears"));
    System.out.println("Get value of pears =" + map.get("pineapple"));
    LinkedHashMap map1 = new LinkedHashMap();
    map1.put("apple", 2);
    map1.put("orange", 3);
    map1.put("mango", 4);
    map1.put("pears", 40);
    map1.put("pineapple", 5);



}

}


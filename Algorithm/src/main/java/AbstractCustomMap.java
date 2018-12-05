public class AbstractCustomMap {
    public static void main(String... args) {
        CustomMap map = new CustomMap();
      /*map.put(1,"apple");
      map.put(2,"orange");
      map.put(3,"mango");
      map.put(1,"pineapple");*/
        map.put("apple", 2);
        map.put("orange", 3);
        map.put("mango", 4);
        map.put("pears", 40);
        map.put("pineapple", 5);
        System.out.println("Get value of orange =" + map.get("orange"));
        System.out.println("Get value of mango =" + map.get("mango"));
        System.out.println("Get value of apple =" + map.get("apple"));
        System.out.println("Get value of pears =" + map.get("pears"));

    }
}

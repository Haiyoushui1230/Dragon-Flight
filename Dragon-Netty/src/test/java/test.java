public class test {
    public static void main(String[] args) {
        String path = "/app/dd/cc";
        String[] split = path.split("/");
        System.out.println(split.length);
        System.out.println(split[0]);
        for (String s : split) {
            System.out.println(s);
        }
    }
}

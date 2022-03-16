package cn.grady.util;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author grady
 * @version 1.0, on 11:39 2022/2/19.
 */
public class StreamTest {

    public static List<Person> personList;

    @BeforeClass
    public static void beforeClass() {
        personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
    }

    /**
     * Optional类是一个可以为null的容器对象。
     * 如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
     */


    // 遍历/匹配（foreach/find/match）
    @Test
    public void streamForeachTest() {
//        遍历/匹配（foreach/find/match）
//        matchTest();
//        筛选（filter）
//        filterTest();
//        聚合（max/min/count)
//        maxTest();

//        映射(map/flatMap)
//        mapFlatMapTest();

//        归约(reduce)

//        reduceTest();

//        收集(collect) collect主要依赖java.util.stream.Collectors 类内置的静态方法。
//        toCollectTest();

//        统计(count/averaging、summarizingDouble、summarizingInt)
//        countTest();

//        分组(partitioningBy/groupingBy)
//        partitionTest();

//        接合(joining)
//        joinTest();

//        排序(sorted)
        sortedTest();

//        提取/组合
        compactTest();


    }

    private void compactTest(){
//        流也可以进行合并、去重、限制、跳过等操作。
        String[] arr1 = { "a", "b", "c", "d" };
        String[] arr2 = { "d", "e", "f", "g" };

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        // concat:合并两个流 distinct：去重
        List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
        // limit：限制从流中获得前n个数据
        List<Integer> collect = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
        // skip：跳过前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());

        System.out.println("流合并：" + newList);
        System.out.println("limit：" + collect);
        System.out.println("skip：" + collect2);

    }


    private void sortedTest(){

//        sorted，中间操作。有两种排序：
//        sorted()：自然排序，流中元素需实现Comparable接口
//        sorted(Comparator com)：Comparator排序器自定义排序
//        将员工按工资由高到低（工资一样则按年龄由大到小）排序」

        // 按工资升序排序（自然排序）
        List<String> newList = personList.stream().sorted(Comparator.comparing(Person::getSalary))
                .map(Person::getName).collect(Collectors.toList());

        // 按工资倒序排序
        List<String> newList2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName).collect(Collectors.toList());

        // 先按工资再按年龄升序排序
        List<String> newList3 = personList.stream().sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge))
                .map(Person::getName).collect(Collectors.toList());

        // 先按工资再按年龄自定义排序（降序）
        List<String> newList4 = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() == p2.getSalary()) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());

        System.out.println("按工资升序排序：" + newList);
        System.out.println("按工资降序排序：" + newList2);
        System.out.println("先按工资再按年龄升序排序：" + newList3);
        System.out.println("先按工资再按年龄自定义降序排序：" + newList4);

    }

    private void joinTest(){
//        joining可以将stream中的元素用特定的连接符（没有的话，则直接连接）连接成一个字符串。
        String names = personList.stream().map(p -> p.getName()).collect(Collectors.joining(","));
        System.out.println("所有员工的姓名：" + names);
        List<String> list = Arrays.asList("A", "B", "C");
        String string = list.stream().collect(Collectors.joining("-"));
        System.out.println("拼接后的字符串：" + string);

    }

    private void partitionTest(){
        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> partSalary = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));

        // 将员工按性别分组
        Map<String, List<Person>> sexGroup = personList.stream().collect(Collectors.groupingBy(Person::getSex));

        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> sexAreaGroup = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("员工按薪资是否大于8000分组情况：" + partSalary);
        System.out.println("员工按性别分组情况：" + sexGroup);
        System.out.println("员工按性别、地区：" + sexAreaGroup);
    }

    private void countTest(){
//        Collectors提供了一系列用于数据统计的静态方法：
//
//        计数：count
//        平均值：averagingInt、averagingLong、averagingDouble
//        最值：maxBy、minBy
//        求和：summingInt、summingLong、summingDouble
//        统计以上所有：summarizingInt、summarizingLong、summarizingDouble
        //统计人数
        Long count = personList.stream().collect(Collectors.counting());
        //平均工资
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        //最高工资
        Optional<Integer> maxSalary = personList.stream().map(Person::getSalary).max(Integer::compare);

        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));

        IntSummaryStatistics summaryStatistics = personList.stream().map(Person::getSalary).collect(Collectors.summarizingInt(s -> s));

        // 一次性统计所有信息
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("员工最大工资：" + maxSalary);
        System.out.println("工资总数：" + sum);
        System.out.println("员工工资总和：" + summaryStatistics);
        System.out.println("员工工资所有统计：" + collect);

    }
    private void toCollectTest(){
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        Set<Integer> set = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));

        Map<?, Person> map = personList.stream().filter(p -> p.getSalary() > 8000)
                .collect(Collectors.toMap(Person::getName, p -> p));
        System.out.println("toList:" + listNew);
        System.out.println("toSet:" + set);
        System.out.println("toMap:" + map);
    }

    private void reduceTest() {

//        求Integer集合的元素之和、乘积和最大值。
//        归约，也称缩减，顾名思义，是把一个流缩减成一个值，能实现对集合求和、求乘积和求最值操作。
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        Optional<Integer> sumx = list.stream().reduce((x, y) -> x + y);
        Optional<Integer> sumy = list.stream().reduce(Integer::sum);
        Integer sum3 = list.stream().reduce(0, Integer::sum);

        //        求乘积
        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);
        // 求最大值方式1
        Optional<Integer> maxx = list.stream().reduce((x, y) -> x > y ? x : y);
        // 求最大值写法2
        Integer maxy = list.stream().reduce(1, Integer::max);

        System.out.println("list求和：" + sumx.get() + "," + sumy.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + maxx.get() + "," + maxy);

//        求所有员工的工资之和和最高工资。」
        Optional<Integer> sumSalary = personList.stream().map(person -> person.getSalary()).reduce(Integer::sum);
// 求工资之和方式2：
        Integer sumSalary2 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(),
                (sum1, sum2) -> sum1 + sum2);
        // 求工资之和方式3：
        Integer sumSalary3 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);

        // 求最高工资方式1：
        Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), Integer::max);
        // 求最高工资方式2：
        Integer maxSalary2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                (max1, max2) -> max1 > max2 ? max1 : max2);

        System.out.println("工资之和：" + sumSalary.get() + "," + sumSalary2 + "," + sumSalary3);
        System.out.println("最高工资：" + maxSalary + "," + maxSalary2);

    }

    private void mapFlatMapTest() {
//        map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
//        flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
//        英文字符串数组的元素全部改为大写。整数数组每个元素+3。」
        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());
        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());
        System.out.println("每个元素大写：" + strList);
        System.out.println("每个元素+3：" + intListNew);

//        将员工的薪资全部增加1000。」
        List<Person> newPersonList = personList.stream().map(person -> {
            Person newPerson = new Person(person.getName(), 0, 0, null, null);
            newPerson.setSalary(person.getSalary() + 1000);
            return newPerson;
        }).collect(Collectors.toList());

        System.out.println("before the first modify: " + personList.get(0).getName() + "--->>" + personList.get(0).getSalary());
        System.out.println("after the first modify: " + newPersonList.get(0).getName() + "--->>" + newPersonList.get(0).getSalary());

        // 改变原来员工集合的方式
        List<Person> personListNew2 = personList.stream().map(person -> {
            person.setSalary(person.getSalary() + 10000);
            return person;
        }).collect(Collectors.toList());
        System.out.println("二次改动前：" + personList.get(0).getName() + "-->" + personList.get(0).getSalary());
        System.out.println("二次改动后：" + personListNew2.get(0).getName() + "-->" + personListNew2.get(0).getSalary());


//        将两个字符数组合并成一个新的字符数组。」
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s -> {
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("before process: " + list);
        System.out.println("after process: " + listNew);

    }

    private void maxTest() {
//        获取String集合中最长的元素。
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");

        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());

        List<Integer> list1 = Arrays.asList(7, 6, 9, 4, 11, 6);
        Optional<Integer> max1 = list1.stream().max(Integer::compareTo);
        Optional<Integer> max2 = list1.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序 max1：" + max1);
        System.out.println("自定义排序 max2：" + max2);

//        获取员工工资最高的人。

        Optional<Person> max3 = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("max salary :" + max3.get().getSalary());

//        计算Integer集合中大于6的元素的个数
        List<Integer> list2 = Arrays.asList(7, 6, 4, 8, 2, 11, 9);

        long count = list2.stream().filter(x -> x > 6).count();
        System.out.println("list2中大于6的元素个数：" + count);

    }

    private void filterTest() {
        //        筛选员工中工资高于8000的人，并形成新的集合。」 形成新集合依赖collect（收集）
        List<String> fiterList = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("salary > 8000's Name :" + fiterList);
    }

    private void matchTest() {
        List<Integer> list = Arrays.asList(7, 6, 5, 4, 3, 8, 1, 9);
        //过滤并遍历
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        //匹配第一个
        Optional<Integer> first = list.stream().filter(x -> x > 6).findFirst();
        //匹配任意（适用于并行流）
        Optional<Integer> any = list.parallelStream().filter(x -> x > 6).findAny();
        boolean anyMatch = list.stream().anyMatch(x -> x > 6);

        System.out.println("first:" + first.get());
        System.out.println("any:" + any.get());
        System.out.println("anyMatch:" + anyMatch);
    }

}

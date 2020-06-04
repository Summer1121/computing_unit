import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import top.summer1121.elastic_computing.client.core.impl.TaskPusher;
import top.summer1121.elastic_computing.common.entity.taskBeans.TaskBean;
import top.summer1121.elastic_computing.common.util.FileUtil;
import top.summer1121.elastic_computing.common.util.MqUtil;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 描述：主程序
 * 目标：八皇后问题
 *
 * @author xtysummer1121@foxmail.com
 * @date 2020/6/3
 */
@SpringBootConfiguration
@ComponentScan("top.summer1121.elastic_computing.*")
public class Main {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
		int n = 11;
		doHandle(n);

//		Integer[] arr = new Integer[n];
//		Arrays.fill(arr,0);
//		List<Integer> array = new ArrayList<>(n);
//		array.addAll(Arrays.asList(arr));
//		List<List<Integer>> result = new ArrayList<>();
//		handler(array, 1);
//		System.out.println(curResult);
	}

	public static List<List<Integer>> invoke(JSONObject data) {
		try {
			List array = data.getJSONArray("list").toJavaList(Integer.class);
			handler(array, 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return curResult;
	}

	public static void doHandle(int n) throws Exception {
		long starttime= new Date().getTime();

		Integer[] arr = new Integer[n];
		List<Integer> array = new ArrayList<>(n);
		Arrays.fill(arr, 0);
		array.addAll(Arrays.asList(arr));
		TaskPusher taskPusher = new TaskPusher();
		List<String> taskIdList = new ArrayList<>();
//		for (int i = 0; i < n; i++) {
		for (int i = 0; i < n; i++) {
			array.set(0, i);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("list", JSON.toJSONString(array));

			File file = FileUtil.writeFileByBytes(jsonObject.toJSONString().getBytes(), "./", "data.json", true);
			List<File> dataList = new ArrayList<>();
			dataList.add(file);

			List<File> classList = new ArrayList<>();
			classList.add(new File("./target\\classes\\Handler.class"));
			classList.add(new File("./target\\classes\\Main.class"));

			TaskBean task = taskPusher.createTaskBean(classList, dataList, null);
			System.out.println(task.getTaskId());
			taskIdList.add(task.getTaskId());
			taskPusher.pushTask(task);
		}

//		Thread.sleep(1000 * 5);
		int i = taskIdList.size() - 1;
		long count = 0l;
		while (!taskIdList.isEmpty()) {
			try {
				String taskId = taskIdList.get(i);
				List<List<Integer>> result = (List<List<Integer>>) taskPusher.pullResult(new TaskBean().setTaskId(taskId)).getResult();
				count += result.size();
				System.out.println(taskId + ":" + result.toString());
				taskIdList.remove(i);
				MqUtil.deleteResultExchange(taskId);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(1000);
			}
			i++;
			if (i >= taskIdList.size()) i = 0;
		}
		System.out.println("计算完成，得到结果["+count+"]组");
		long endTime=new Date().getTime();

		System.out.println("用时["+(endTime-starttime)/1000+"]秒");
	}

	public static List<List<Integer>> curResult = new ArrayList<>();

	/**
	 * 进行递归计算
	 *
	 * @param array 当前的皇后布局
	 * @param index 当前流程讨论的索引
	 * @return {@link List< List< Integer>>}
	 * @author xtysummer1121@foxmail.com
	 * @date 2020/6/3
	 */
	static void handler(List<Integer> array, Integer index) {
		int n = array.size();

		for (int i = 0; i < n; i++) {
			boolean flag = true;
			//校验i位置与已经存在的皇后位，是否有冲突
			for (int j = 0; j < index; j++) {
				//如果有同列 或者 有对角线
				if (i == array.get(j) || (i - array.get(j)) == Math.abs(index - j)) {
					flag = false;
					break;
				}
			}
			//如果当前位置不能放，检索下一个位置
			if (!flag) continue;
			//当前的i值是可放置的
			List<Integer> newArray = new ArrayList<>(n);
			newArray.addAll(array);
			newArray.set(index, i);
			//如果是最后一位皇后
			if (index == n - 1) {
				curResult.add(newArray);
			}
			//否则递归继续找
			else {
				//创建一个当前位置放置的列表
				handler(newArray, index + 1);
			}
		}
	}
}

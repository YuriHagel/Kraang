package kraang.util;

import kraang.exception.KraangException;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CollectionUtils {
  public static <T> List<List<T>> listSlice(List<T> list, int listSize) {
    if (org.springframework.util.CollectionUtils.isEmpty(list)) {
      throw new KraangException("list is mandatory");
    }
    List<List<T>> resultList = new ArrayList<>();
    for (int i = 0; i < list.size(); i += listSize) {
      List<T> chunk = new ArrayList<>(list.subList(i, Math.min(list.size(), i + listSize)));
      resultList.add(chunk);
    }
    return resultList;
  }
}

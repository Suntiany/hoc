package dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 迎合echart里面的series项
 */
public class EchartSeries {
    private String name;
    private String type;
    private List<Integer> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}

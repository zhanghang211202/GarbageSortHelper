package top.miyamoto.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/2/14 0014 9:10
 */
@Log4j2
public class ExcelListener extends AnalysisEventListener {
    private List<Object> data = new ArrayList<Object>();
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        System.out.println(analysisContext.getCurrentSheet());
        data.add(o);
        if(data.size()>=100){
            print();
            data = new ArrayList<Object>();
        }
    }

    public void print(){
        for (Object o:data) {
            log.info(o);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        print();
    }
}

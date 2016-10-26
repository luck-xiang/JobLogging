package com.kxiang.job.select;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称:frontdesk
 * 创建人:kexiang
 * 创建时间:2016/10/12 10:02
 */
public class CheckTypeBean implements Serializable {
    private String type;
    private List<CheckBean> checkList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CheckBean> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<CheckBean> checkList) {
        this.checkList = checkList;
    }

    public static class CheckBean implements Serializable {
        private String name;
        private boolean checkSelect;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCheckSelect() {
            return checkSelect;
        }

        public void setCheckSelect(boolean checkSelect) {
            this.checkSelect = checkSelect;
        }
    }
}

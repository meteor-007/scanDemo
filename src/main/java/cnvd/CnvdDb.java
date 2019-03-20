package cnvd;

import lombok.Data;

/**
 * cnvd_db表对应的模型类
 *
 * @author zml
 * @date 2018-7-24
 */
@Data
public class CnvdDb {

    private Integer id;

    private String title;

    private String cnvdId;

    private String releaseTime;

    private String hazardLevel;

    private String attackRoute;

    private String attackComplexity;

    private String certification;

    private String confidentiality;

    private String integrity;

    private String availability;

    private Float score;

    private String affectedProduct;

    private String bugtraqId;

    private String bugtraqLink;

    private String otherId;

    private String cveId;

    private String cveLink;

    private String description;

    private String referenceLink;

    private String solution;

    private String patch;

    private String patchLink;

    private String verifyMessage;

    private String reportingTime;

    private String inclusionTime;

    private String updateTime;

    private String annex;

    private String url;
}

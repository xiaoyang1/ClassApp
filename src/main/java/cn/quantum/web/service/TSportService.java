package cn.quantum.web.service;

import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;

public interface TSportService {

    ResultVO getCount(TUserVO userVO);

    ResultVO getSportStatus(TUserVO userVO);

    ResultVO sportToday(TUserVO userVO, boolean b);

    ResultVO getSportInfo(TUserVO userVO);

    ResultVO  subscribeToday(TUserVO userVO, boolean b);
}

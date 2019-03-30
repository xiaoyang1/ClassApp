package cn.quantum.web.service;

import cn.quantum.web.vo.ResultVO;
import cn.quantum.web.vo.TUserVO;

import java.util.Set;


public interface EarlyGetUpService {

    ResultVO checkInTomorrow(TUserVO userVO, boolean b);

    ResultVO getCount(TUserVO userVO);

    ResultVO getCheckInStatus(TUserVO userVO);

    ResultVO checkInToday(TUserVO userVO, boolean b);

    ResultVO getCheckInInfo(TUserVO userVO);

    Set<Integer> getSuccessIds();

    Set<Integer> getFailedIds();
}

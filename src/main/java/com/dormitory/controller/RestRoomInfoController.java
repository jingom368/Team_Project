package com.dormitory.controller;

import com.dormitory.dto.*;
import com.dormitory.service.DormitoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000/" })
@RestController
@RequiredArgsConstructor
@RequestMapping("/roomInfo")
public class RestRoomInfoController {
    private final DormitoryService service;

    //1. 숙소 제일 위, 방 전체 정보 출력(사진, 등)
    @GetMapping("/roomReview")
    public List<DormitoryRoomDTO> getRoomReviewInfo(DormitoryDTO dormitory){
        String d_code= dormitory.getD_code();
        return service.getRoomReviewInfo(d_code);
    }

    //2. 리뷰 테이블
    @GetMapping("/review")
    public List<ReviewDTO> getReview(DormitoryDTO dormitory){
        String d_code= dormitory.getD_code();
        return  service.getReview(d_code);
    }

    //3. 지도 위도/경도
    @GetMapping("/map")
    public DormitoryDTO getMap(DormitoryDTO dormitory){

        String d_code = dormitory.getD_code();
        return service.getMap(d_code);

    }

    //4. 객실 정보
    @GetMapping("/roomDetail")
    public Map<String, Object> getRoomDetail(DormitoryDTO dormitory){
        String d_code = dormitory.getD_code();
        List<RoomDTO> list = service.getR_Code(d_code);
        Map<String,Object> data = new HashMap<>();

        for(int i=0;i<list.size();i++){
            RoomDTO room = list.get(i);

            data.put(room.getR_code(),service.getUrl(room));
        }


        return data;
    }
    //5. 숙소 정보
    @GetMapping("/dormitory")
    public List<DormitoryDTO> getDormitory(DormitoryDTO dormitory){
        String d_code = dormitory.getD_code();
        return service.getDormitory(d_code);
    }
    //6. 비품 정보
    @GetMapping("/amenity")
    public List<AmenityDTO> getAmenity(DormitoryDTO dormitory){
        String d_code = dormitory.getD_code();
        return service.getAmenity(d_code);
    }

    @GetMapping("/cancel")
    public Map<String, String> getCancel(DormitoryDTO dormitory){
        List<CancelDTO> cancel = service.getCancelPolicy(dormitory);
        String cancelPolicy1="성수기 규정 enter";
        String cancelPolicy0="비수기 규정 enter";
        for(int i=0;i<cancel.size();i++){
            if(cancel.get(i).getC_type() == 1){
                cancelPolicy1 += "예약일 "+cancel.get(i).getC_policy_apply_date()+"일 전, "
                        +cancel.get(i).getC_time()+"시 기준으로 "
                        +(100-cancel.get(i).getC_rate()) + "%만 환불됩니다.enter";
            }
            else if(cancel.get(i).getC_type() == 0){
                cancelPolicy0 += "예약일 "+cancel.get(i).getC_policy_apply_date()+"일 전, "
                        +cancel.get(i).getC_time()+"시 기준으로 "
                        +(100-cancel.get(i).getC_rate()) + "%만 환불됩니다.enter";
            }
        }
        Map<String, String> data = new HashMap<>();
        data.put("policy0",cancelPolicy0);
        data.put("policy1",cancelPolicy1);
        return data;
    }


}

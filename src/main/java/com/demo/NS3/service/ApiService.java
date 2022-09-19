package com.demo.NS3.service;

import com.demo.NS3.entity.BodyEntity;
import com.demo.NS3.entity.EventCodeEntity;
import com.demo.NS3.entity.Ns3Entity;
import com.demo.NS3.repository.BodyRepository;
import com.demo.NS3.repository.EventCodeRepository;
import com.demo.NS3.repository.Ns3Repository;
import com.demo.NS3.vo.ApiBodyVo;
import com.demo.NS3.vo.ApiFaceVo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Async;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {
    private final Ns3Repository repository;
    private final BodyRepository bodyRepository;
    private final EventCodeRepository codeRepository;
    public ResponseEntity<?> saveData(MultipartHttpServletRequest request)throws Exception{

        log.info("======== Data push ===========");
        MultipartFile snap = request.getFile("snap");
        String json = request.getParameter("json");
        JsonNode parent = new ObjectMapper().readTree(json);
        String msgId = String.valueOf(parent.findValue("msg_id"));   //774(snap) or 775(heartbeat)
        ObjectMapper ob = new ObjectMapper();
        String path = "C:\\VueTest\\img\\";
        if(msgId.equals("774")){
            //TODO: DB에 face관련 테이블 안만들어져있음, face_attr 사용여부 확인 후 수정
            if(parent.findValue("face_attr")!=null){ //Face
                System.out.println();
                System.out.println("FACE Data Pushed");
                String data = String.valueOf(parent.findValue("data"));
                ApiFaceVo vo = ob.readValue(data,ApiFaceVo.class);
                System.out.println("Face Vo::"+vo);
                System.out.println(vo.getFace_attr());
                System.out.println();
            }
            else {                                            //Body
                String data = String.valueOf(parent.findValue("data"));
                ApiBodyVo vo = ob.readValue(data, ApiBodyVo.class);

                if( !vo.getEvents_type().isEmpty()){
                    log.info("Event Data Pushed");
//                    log.info("Body vo::" + vo);

                    String deviceID = vo.getDevice_id();
                    String cameraID = vo.getCamera_name();
                    String trigger = vo.getTrigger();
                    String date = trigger.split(" ")[0];
                    String path1 = path+File.separator+deviceID+File.separator+cameraID+File.separator+date+File.separator;
                    String filename = vo.getSnap_id()+".jpg";
                    File imagePath = new File(path1);
                    if(imagePath.exists()==false){ imagePath.mkdirs(); }
                    try{
                        snap.transferTo(new File(imagePath,filename));
                    }catch (NullPointerException e){
                        System.out.println("Snap not exist");
                    }
                    BodyEntity entity = new BodyEntity(vo);
//                    Ns3Entity entity = new Ns3Entity(vo);
//                    entity.setSnap_path(imagePath+File.separator+filename);
//                    repository.save(entity);
                    entity.setSnap_feat(imagePath+File.separator+filename);
                    bodyRepository.save(entity);
                }
            }
        }
        else{
            log.info("HeartBeat");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public @ResponseBody byte[] getImage(String deviceID, String cameraname, String date) throws IOException {
        BodyEntity entity = bodyRepository.findByDeviceidAndCameranameAndTrigger(deviceID, cameraname, date).orElseThrow();
        String pathdata = entity.getSnap_feat();
        InputStream is = new FileInputStream(pathdata);
        byte[] imageByte = is.readAllBytes();
        return imageByte;
    }

//    @Scheduled(cron = "30 * * * * *")
//    public void scheduleTest(){
//        log.info("SCHEDULING TEST");
//        List<Ns3Entity> list = repository.findByFlag("N");  //msg전송안한 데이터 가져옴
//        if(list.isEmpty()){
//            System.out.println("No events occured");
//        }
//        else{
//            for(Ns3Entity entity:list){
//                EventCodeEntity codelist = codeRepository.findByEventtype(entity.getEvents_type()).orElseThrow();
//                System.out.println("events message::"+codelist.getEventvalue());
//                entity.setFlag("Y");
//                repository.save(entity);
//
//            }
//        }
//
//    }

}

package com.demo.KaKao.api;

import com.demo.KaKao.entity.TokenEntity;
import com.demo.KaKao.repository.TokenRepository;
import com.demo.KaKao.service.SendMessageService;
import com.demo.NS3.entity.BodyEntity;
import com.demo.NS3.entity.Ns3Entity;
import com.demo.NS3.repository.BodyRepository;
import com.demo.NS3.repository.EventCodeRepository;
import com.demo.NS3.repository.Ns3Repository;
import com.demo.NS3.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageScheduler {
    private final SendMessageService messageService;
    private final TokenRepository tokenRepository;
    private final BodyRepository bodyRepository;
    private final EventCodeRepository codeRepository;
    private final SiteRepository siteRepository;

    @Scheduled(cron = "30 * * * * *")
    public void kakaoMsg(){
        String site = "";
        String device = "";
        String camera = "";
        String eventmsg = "";
        String occurTime="";
        String snapid="";

        log.info("**********scheduler*************");
        List<BodyEntity> pushData = bodyRepository.findBySendflag("N");
        if(pushData.isEmpty())
            log.info("No events Occured");
        else{
            log.info(String.valueOf(pushData.size()));
            TokenEntity token = tokenRepository.findByLoginyn("Y").orElseThrow();
            String accessToken = token.getAccesstoken();
            for(BodyEntity list:pushData){
                device = list.getDeviceid();
                camera = list.getCameraname();
                occurTime = list.getTrigger();
                snapid = list.getSnapid();
                eventmsg = codeRepository.findByEventtype(list.getEvents_type()).get().getEventvalue();
                site = siteRepository.findByDeviceid(device).get().getSitename();
                try{
                    messageService.sendMessage(accessToken,device,camera,eventmsg,site,occurTime,snapid);
                }
                catch (Exception e){
                    log.info("sendMessage Error::"+e);
                }
                list.setSendflag("Y");
                bodyRepository.save(list);
            }

        }
    }
}

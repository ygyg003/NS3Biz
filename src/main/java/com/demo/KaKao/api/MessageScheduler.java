package com.demo.KaKao.api;

import com.demo.KaKao.entity.TokenEntity;
import com.demo.KaKao.repository.TokenRepository;
import com.demo.KaKao.service.SendMessageService;
import com.demo.NS3.entity.Ns3Entity;
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
    private final Ns3Repository ns3Repository;
    private final EventCodeRepository codeRepository;
    private final SiteRepository siteRepository;

    @Scheduled(cron = "30 * * * * *")
    public void kakaoMsg(){
        String site = "";
        String device = "";
        String camera = "";
        String eventmsg = "";
        String occurTime="";

        log.info("**********scheduler*************");
        List<Ns3Entity> pushData = ns3Repository.findByFlag("N");
        if(pushData.isEmpty())
            log.info("No events Occured");
        else{
            log.info(String.valueOf(pushData.size()));
            TokenEntity token = tokenRepository.findByLoginyn("Y").orElseThrow();
            String accessToken = token.getAccesstoken();
            for(Ns3Entity list:pushData){
                device = list.getDeviceid();
                camera = list.getCameraname();
                occurTime = list.getTrigger();
                eventmsg = codeRepository.findByEventtype(list.getEvents_type()).get().getEventvalue();
                site = siteRepository.findByDeviceid(device).get().getSitename();
                try{
                    messageService.sendMessage(accessToken,device,camera,eventmsg,site,occurTime);
                }
                catch (Exception e){
                    log.info("sendMessage Error::"+e);
                }
                list.setFlag("Y");
                ns3Repository.save(list);
            }

        }
    }
}

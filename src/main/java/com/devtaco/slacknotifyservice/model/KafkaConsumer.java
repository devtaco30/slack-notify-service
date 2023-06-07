package com.devtaco.slacknotifyservice.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.devtaco.slacknotifyservice.entity.MemeberSlackId;
import com.devtaco.slacknotifyservice.service.DataManager;
import com.devtaco.slacknotifyservice.service.SlackClientService;
import com.devtaco.slacknotifyservice.utils.MessageConverter;
import com.slack.api.methods.SlackApiException;

/**
 * 실질적인 메인 서비스. <p>
 * Kafka 에 새로운 item 이 올라오면 listener 가 받아서 <p>
 * slack notify 와 db 에 적재하는 작업을 진행한다.
 */
@Component
public class KafkaConsumer {

    private DataManager dataManager;
    
    private SlackClientService slackService;

    public KafkaConsumer(DataManager dataManager, SlackClientService slackService) {
        this.dataManager = dataManager;
        this.slackService = slackService;
    }

    @KafkaListener(topics = "${devtaco.app.kafka-snts-topic}", groupId = "${devtaco.app.kafka-snts-group}")
    public void consume(String payload) throws IOException, SlackApiException {
        // 1. payload (json string) -> AppMessage 로 Convert
        AppMessage appMessage = convert2AppMessage(payload);

        // 2. member email 을 통해 slack id 를 search 하고 set
        setToSlackIdListByMemberEmail(appMessage);

        // 3. destination 으로 slack message send
        slackService.sendMessage(appMessage.getSlackChannelId(), getSlackMessgeString(appMessage));
    }

    /**
     * payload 를 AppMessage 로 mapping 한다.
     * 
     * @param payload
     * @return
     */
    private AppMessage convert2AppMessage(String payload) {
        AppMessage appMessage = MessageConverter.createAppMessage(payload);
        return appMessage;
    }

    /**
     * message 에 있는 memeberEmail 로 slack Id 를 조회하여 알림을 보낼 member slack id list 를 set
     * 
     * @param appMessage
     */
    private void setToSlackIdListByMemberEmail(AppMessage appMessage) {
        List<String> toSlackIds = new ArrayList<>();
        Iterator<String> toIterator = appMessage.getToEmailList().iterator();
        while (toIterator.hasNext()) {
            String memberEmail = toIterator.next();
            MemeberSlackId memberSlackId = dataManager.findSlackIdByEmail(memberEmail).getSlackId();
            toSlackIds.add(memberSlackId.getMemberSlackId());
        }
        appMessage.setToSlackIdList(toSlackIds);
    }

    /**
     * Slack Message Block String 을 만든다.
     * 
     * @param appMessage
     * @return
     */
    private String getSlackMessgeString(AppMessage appMessage) {
        return MessageConverter.createSlackMessageBlockString(appMessage);
    }

}

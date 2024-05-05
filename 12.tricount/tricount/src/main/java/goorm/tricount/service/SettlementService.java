package goorm.tricount.service;

import goorm.tricount.domain.Join;
import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.repository.JoinRepository;
import goorm.tricount.repository.SettlementRepository;
import goorm.tricount.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final JoinRepository joinRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long joinCreateSettlement(Long userId, String settlementName) {

        Settlement settlement = Settlement.createSettlement(settlementName);
        settlementRepository.save(settlement);

        User user = userRepository.findOne(userId);

        Join join = Join.join(user, settlement);
        joinRepository.save(join);

        return settlement.getId();
    }

    @Transactional
    public void joinSettlement(Long settlementId, Long userId) {

        Settlement settlement = settlementRepository.findOne(settlementId);

        User user = userRepository.findOne(userId);

        Join join = Join.join(user, settlement);
        joinRepository.save(join);
    }

    @Transactional
    public void deleteSettlement(Long settlementId) {
        Settlement settlement = settlementRepository.findOne(settlementId);
        settlement.delete();
    }

    public List<Settlement> getSettlementList(Long userId) {
        return settlementRepository.findAllByUserId(userId);
    }

    public Settlement getSettlement(Long settlementId) {
        return settlementRepository.findOne(settlementId);
    }
}

package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.tobe.model.pass.seat.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.seat.StudyCafeSeatPasses;
import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler studyCafeIOHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            studyCafeIOHandler.showWelcomeMessage();
            studyCafeIOHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = selectPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            StudyCafePassOrder passOrder = StudyCafePassOrder.of(
                selectedPass,
                optionalLockerPass.orElse(null)
            );

            studyCafeIOHandler.showPassOrderSummary(passOrder);
        } catch (AppException e) {
            studyCafeIOHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            studyCafeIOHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeSeatPass selectPass() {
        StudyCafePassType passType = studyCafeIOHandler.askPassTypeSelecting();

        List<StudyCafeSeatPass> passCandidates = findPassCandidatesBy(passType);

        return studyCafeIOHandler.askPassSelecting(passCandidates);
    }

    private List<StudyCafeSeatPass> findPassCandidatesBy(StudyCafePassType passType) {
        StudyCafeSeatPasses allPasses = studyCafeFileHandler.readStudyCafePasses();

        return allPasses.findPassBy(passType);
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidate(selectedPass);

        if (lockerPassCandidate.isPresent()) {
            StudyCafeLockerPass lockerPass = lockerPassCandidate.get();

            boolean isLockerSelected = studyCafeIOHandler.askLockerPass(lockerPass);
            if (isLockerSelected) {
                return Optional.of(lockerPass);
            }
        }
        
        return Optional.empty();
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidate(StudyCafeSeatPass pass) {
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();
        return allLockerPasses.findLockerPassBy(pass);
    }
}

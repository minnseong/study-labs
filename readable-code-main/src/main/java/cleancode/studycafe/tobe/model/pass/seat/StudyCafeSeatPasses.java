package cleancode.studycafe.tobe.model.pass.seat;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import java.util.List;

public class StudyCafeSeatPasses {

    private final List<StudyCafeSeatPass> passes;

    private StudyCafeSeatPasses(List<StudyCafeSeatPass> passes) {
        this.passes = passes;
    }

    public static StudyCafeSeatPasses of(List<StudyCafeSeatPass> passes) {
        return new StudyCafeSeatPasses(passes);
    }

    public List<StudyCafeSeatPass> findPassBy(StudyCafePassType passType) {
        return passes.stream()
            .filter(studyCafePass -> studyCafePass.isSamePassType(passType))
            .toList();
    }
}
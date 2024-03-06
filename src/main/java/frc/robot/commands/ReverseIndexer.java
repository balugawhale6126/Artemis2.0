
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Indexer.IndexerStates;

public class ReverseIndexer extends Command {
    private final Indexer s_Indexer;
    IndexerStates state;

    public ReverseIndexer() {
        s_Indexer = Indexer.getInstance();

        addRequirements(s_Indexer);
    }


    @Override
    public void initialize() {
        s_Indexer.setSpeed(-0.5);

    }

    @Override
    public void end(boolean interrupted) {
        s_Indexer.setSpeed(0);

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
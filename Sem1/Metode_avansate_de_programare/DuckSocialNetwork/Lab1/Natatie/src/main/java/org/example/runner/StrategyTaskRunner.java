package org.example.runner;

import org.example.domain.Container;
import org.example.domain.Strategy;
import org.example.domain.Task;
import org.example.factory.TaskContainerFactory;

public class StrategyTaskRunner implements TaskRunner
{
    private Container container;

    public StrategyTaskRunner(Strategy strategy)
    {
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask()
    {
        if (!container.isEmpty())
        {
            Task task = container.remove();
            if (task != null)
            {
                task.execute();
            }
        }
    }

    @Override
    public void executeAll()
    {
        while (!container.isEmpty())
        {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task task)
    {
        container.add(task);
    }

    @Override
    public boolean hasTask()
    {
        return !container.isEmpty();
    }
}

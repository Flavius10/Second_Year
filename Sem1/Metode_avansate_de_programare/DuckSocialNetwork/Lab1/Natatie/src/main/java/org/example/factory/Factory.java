package org.example.factory;
import org.example.domain.Container;
import org.example.domain.Strategy;

public interface Factory
{
    Container createContainer(Strategy strategy);
}

# TaskMachine

TaskMachine is a robust and flexible hybrid State Machine/TaskBot framework designed specifically for RuneMate. It
seamlessly integrates the functionality of a state machine and a task bot, providing a powerful tool for creating
complex and efficient RuneMate bots. With TaskMachine, developers can easily manage and control bot tasks, allowing for
a more streamlined and organized approach to bot creation. The framework's hybrid nature allows for greater
adaptability, catering to a wide range of bot development needs. Whether you're building a simple bot for a single task
or a complex bot capable of handling multiple tasks simultaneously, TaskMachine provides the structure and functionality
needed to make the process as smooth as possible.

# Usage

```kotlin


class WoodcuttingExample : TaskMachine() {
    ...
    override fun setDefaultState(): State {
        return DefaultState()
    }
}

```

To get started, extend your entry class with `TaskMachine()`
From here you only need to define your default state by overloading `setDefaultState()`.

## States

```kotlin
class DefaultState : State() {

    override fun onStart() {
        ...
    }

    override fun execute() {
        ...
    }

    override fun onExit() {
        ...
    }

}
```

This is a state, it is defined by extending `State()`. States provide overrides to

- onStart() This method gets called when your state is first entered
- onExit() gets called when a transition is successful and a new current state is being set
- execute() gets called every loop until the state is exited.

## Transitions

States need a way to conditionally change states, right? Enter the Transition.

```kotlin
class ExampleTransition : Transition() {
    override fun validate() {
        return condition
    }
    override fun transitionTo() {
        return NewState()
    }
}
```

Transitions are a way to transition from your current state to another.
You can create a Transition by overloading a class with the Transition interface. This implements the following methods

- validate() - Use this method to return whether or not we should transition.
- transitionTo - This method defines the state we would like to transition to when the validate function passes.

### defineTransitions()

Well that's all fine and dandy, but how do we go about implementing these transitions in our state?

```kotlin
class DefaultState : State() {

    override fun defineTransitions() {
        addTransition(ExampleTransition())
        addTransition(AnotherTransition())
    }

    ...

}
```

To define transitions we can override the `defineTransitions()` method. Using `addTransition()`, we can add as many
transitions as needed. The transitions will be validated in the order they are added.

### @TransitionField()

We do have another technique available to use for adding transitions to our state.

```kotlin
class DefaultState : State() {

    @TransitionField(order = 1)
    val toExampleState = ExampleTransition()

    @TransitionField(order = 2)
    val toAnotherState = AnotherTransition()

    ...

}
```

We can use the TransitionField annotation to easily tell the state that we want to add the field
To the list of available transitions.

&ast; The "order" parameter is important here as that will determine which order your
Transitions are validated in, order can be omitted if there is only one TransitionField defined.

&ast;&ast; the field names (toExampleState, toAnotherState) are inconsequential. Name them something that best helps you
determine what the transition is doing, at a glance.

### GenericTransition

For ease of use we also have a GenericCondition available to us that takes Lambdas to define the validate and
transitionsTo fields of our Transition

```kotlin
class DefaultState : State() {

    @TransitionField(order = 1)
    val toAnotherState = GenericTransition({ condition }, { AnotherState() })

    ...

}
```

This provides an interface for defining transitions without creating a new class.

## Task States

```kotlin
class Starting : TaskState() {
    @TransitionField(order = 1)
    val toAnotherState = GenericTransition({ condition }, { AnotherState() })

    @TaskField(order = 1)
    val doSomething = DoSomething()

    @TaskField(order = 2)
    val doSomething = DoSomethingElse()
}
```

Extending TaskState adds TaskBot like functionality to your states.
You can add tasks with the TaskField annotation. Similarly to TransitionField, the order parameter is important here.
Tasks are evaluated in order every loop until a tasks validation evaluates to true, then the true task is executed.

It is important to note that if you want to override "execute()" on the TaskState(), you will need to call the super.
Tasks will not be evaluated and executed, otherwise.

### Task

```kotlin
class DoSomething : Task {
    override fun validate(): Boolean {
        return someThing != someThingElse
    }

    override fun execute() {
        ...
    }
}
```

You can create a task By extending the Task interface. This will implement two methods

- validate() will evaluate a condition to determine whether the task should be executed.
- execute() will run after validate evaluates to true.

### defineTasks()

You may also define your tasks by overloading the defineTasks() method

```kotlin
class SampleState : TaskState() {
    ...

    override fun defineTasks() {

        addTask(DoSomething())
        addTask(DoSomethingElse())

        // It is only necessary to call the super for defineTasks if you still want to use TaskField annotations.
        super.defineTaks()
    }
}
```

## Event Listeners

```kotlin

class ExampleState : State(), EngineListener {
    ...
}
```

TaskMachine States and TaskStates will automatically handle registering and unregistering listeners to the dispatch
handler, all you need to do is implement the interface on your state.

## Dependency Injection

TaskMachine ships with a very simple DIContainer implementation.

```kotlin
class ExampleState : State() {
    val bot: ExampleBot by injected()
}
```

Your bot, extended by TaskMachine, automatically registers itself with the dependency container and you can access it
from anywhere.
Let's break this down `val bot: ExampleBot by injected()`
adding the `by injected()` operator to this field will retrieve an instance with type of ExampleBot from the registry. 

You can even register other types. Anything, really.

```kotlin
class ExampleState : State() {
    override fun onStart() {
        DIContainer.register(this)
    }
}
```
This will register your ExampleState with the Dependency Container. now you can do this

```kotlin
val state: ExampleState by injected()
```

This makes your state convenient to access from your tasks!

You can unregister from the container as well!

```kotlin
class Example: State() {
    override fun onExit() {
        DIContainer.unregister(this::class)
    }
}
```

---
## What's next?

I have created a very simple woodcutting bot as an example in the examples directory of this repo. Take a look for some
inspiration and to see it in action!
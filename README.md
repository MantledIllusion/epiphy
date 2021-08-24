# epiphy
Epiphy is a lightweight library for property based data model access.

Epiphytes are pseudo-parasitic plants. They live on the surface of other organisms in order to benefit from their environment, but do not break that organism's surface, leaving the host unharmed. This prevents the host from getting injuries and diseases from the symbiosis, effectively extending the epiphyte life's length.

```xml
<dependency>
    <groupId>com.mantledillusion.data</groupId>
    <artifactId>epiphy</artifactId>
</dependency>
```

Get the newest version at [mvnrepository.com/epiphy](https://mvnrepository.com/artifact/com.mantledillusion.data/epiphy) 

## Possible applications

Epiphy is used to abstract data models.

A model is the reproduced structure for a specific type of data. Hence, models can come in any shape or form, but they are always made up of the same base elements:
- simple values that hold raw data, such as integers or strings
- complex types with sub elements of any type, such as POJOs
- collections of elements of any type, such as lists or maps

Abstracting such models comes down to abstracting these element types and allowing arbitrary combinations of them, which is exactly what epiphy allows.

For example, a data model might look like this:

```java
class OwnerType {
    static ModelPropertyList<OwnerType, CarType> CARS = ModelPropertyList.fromObject(owner -> owner.car);
    static ModelProperty<OwnerType, CarType> CAR = CARS.append(ModelProperty.fromList());

    CarType car;
}

class CarType {
    static ModelProperty<CarType, EngineType> ENGINE = ModelProperty.fromObject(car -> car.engine);
    static ModelPropertyList<CarType, WheelType> WHEELS = ModelPropertyList.fromObject(car -> car.wheels);
    static ModelProperty<CarType, WheelType> WHEEL = WHEELS.append(ModelProperty.fromList());

    EngineType engine;
    List<WheelType> wheels;
}

class EngineType {
    static ModelProperty<EngineType, ControlUnitType> CONTROL_UNIT = ModelProperty.fromObject(engine -> engine.controlUnit);
    static ModelProperty<EngineType, Integer> HP = ModelProperty.fromObject(engine -> engine.hp);

    ControlUnitType controlUnit;
    Integer hp;
}

class ControlUnitType {
    static ModelProperty<ControlUnitType, Short> RPM = ModelProperty.fromObject(controlUnit -> controlUnit.rpm);

    Short rpm;
}

class WheelType {
    static ModelProperty<WheelType, RimType> RIM = ModelProperty.fromObject(wheel -> wheel.rim);
    static ModelProperty<WheelType, TireType> TIRE = ModelProperty.fromObject(wheel -> wheel.tire);

    RimType rim;
    TireType tire;
}

class RimType {
    static ModelProperty<RimType, Boolean> IS_ALLOY = ModelProperty.fromObject(rim -> rim.isAlloy);

    Boolean isAlloy;
}

class TireType {
    static ModelProperty<TireType, Integer> PRESSURE = ModelProperty.fromObject(tire -> tire.pressure);

    Integer pressure;
}
```

As seen above, each of the type's fields is accompanied by a static **_ModelProperty_** accessor.

The following chapters will detail the advantages for using epiphy on the example of the model above.

### Null Safety

Ensuring null safety is crucial to smooth running java programs, but when the data model to work with is rather deep, things can get cumbersome.

For example, when trying to get the RPM of a car's engine in the example model, a method might look like this:

```java
Short getRpm(Car car) {
    if (car != null) {
        if (car.engine != null) {
            if (car.engine.controlUnit != null) {
                return car.engine.controlUnit.rpm;
            }
        }
    }
    return null;
}
```

Using epiphy, the code both shortens and simplifies a lot:

```java
Short getRpm(Car car) {
    return CarType.ENGINE
        .append(EngineType.CONTROL_UNIT)
        .append(ControlUnitType.RPM)
        .get(car);
}
```

### Collection Handling

When there are (possibly multiple) collections in the layers of a data model's tree structure, nested loops are often inevitable when working with these collection's elements.

For example, when trying to determine the amount of alloy wheels someone ones, the code (**even without any null check!**) would look something like:

```java
Long countAlloyRims(OwnerType owner) {
    Long count = 0;
    for (CarType car: owner.cars) {
        for (WheelType wheel: car.wheels) {
            if (wheel.rim.isAlloy) {
                count++;
            }
        }
    }
    return count;
}
```

Since epiphy allows iterating and streaming of properties, the code gets much easier:

```java
Long countAlloyRims(OwnerType owner) {
    return OwnerType.CAR
        .append(CarType.WHEEL)
        .append(WheelType.RIM)
        .stream(owner)
        .filter(RimType.IS_ALLOY::get)
        .count();
}
```

### Abstracting Functionality

If one would like to find out the max amount of horsepower in all of an owner's cars, or their highest tire pressure, two separate functions would have to be written, even though the base functionality of determining an average over a number is the same.

Since epiphy abstracts a model's properties, a single function would be enough to average any of an owner's car properties:

```java
Integer max(OwnerType owner, ModelProperty<OwnerType, Integer> property) {
    return property.stream(owner)
        .mapToInt(Integer::intValue)
        .max();
}
```

This method could then be simply called differently:

```java
Integer maxHp = max(owner, OwnerType.CAR
        .append(CarType.ENGINE)
        .append(EngineType.HP));

Integer maxPressure = max(owner, OwnerType.CAR
        .append(CarType.WHEEL)
        .append(WheelType.TIRE)
        .append(TireType.PRESSURE));
```
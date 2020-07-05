# epiphy
Epiphy is a lightweight library for property based data model access.

Epiphytes are pseudo-parasitic plants. They live on the surface of other organisms in order to benefit from their environment, but do not break that organism's surface, leaving the host unharmed. This prevents the host from getting injuries and diseases from the symbiosis, effectively extending the epiphyte life's length.

## Possible applications

There are 2 main applications for epiphy:
- To prevent boilerplate code when handling very complex data models on a lot of different places in an application
- To generalize code of frameworks that have to handle abstracted model structures provided by the framework user

## Data Model Abstraction

A model is the reproduced structure for a specific type of data. Hence, models can come in any shape or form, but they are always made up of the same base elements:
- simple values that hold raw data, such as integers or strings 
- complex types with sub elements of any type, such as POJOs
- collections of elements of any type, such as lists or maps

As a result, abstracting models comes down to abstracting these element types and allowing arbitrary combinations of them, which is exactly what epiphy allows:

```java
public class Pojo {

    public static final ModelProperty<Pojo, Integer> ID = ModelProperty.fromObject(Pojo::getValue);
    public static final ModelPropertyList<Pojo, OtherPojo> SUBOBJECTS = ModelPropertyList.fromObject(Pojo::getSubObjects);
    public static final ModelProperty<Pojo, OtherPojo> SUBOBJECT = SUBOBJECTS.append(ModelProperty.fromList());
    public static final ModelProperty<Pojo, String> SUBOBJECT_VALUE = SUBOBJECT.append(OtherPojo.VALUE);

    private Integer id;
    private List<OtherPojo> subObjects;

    ...
}

public class OtherPojo {

    public static final ModelProperty<OtherPojo, String> VALUE = ModelProperty.fromObject(OtherPojo::getValue);
    
    private String value;

    ...
}
```

Depending on its type, each property allows generalized operations on its model.

For example by using the **_Pojo_.SUBOBJECT_VALUE** property of the code above, it would be possible to stream through all _**OtherPojo**.value_ field's values of a parent **_Pojo_** instance by simply calling:

```java
Pojo.SUBOBJECT_VALUE.stream(pojoInstance).forEach(*code*)
```

In relation, the code without properties would be:

```java
if (pojoInstance != null) {
    for (OtherPojo otherPojoInstance: pojoInstance.getSeubObjects()) {
        if (otherPojoInstance != null && otherPojoInstance.getValue() != null) {
            *code*
        }
    }
}
```

These simplifications will become more and more versatile the deeper and more complex a model is.
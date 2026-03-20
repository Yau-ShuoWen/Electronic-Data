package Tool.DataStructure.tuple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.function.Function;

@EqualsAndHashCode
public class Result<T, E>
{
    @Getter
    private final boolean success;

    private final T value;

    private final E error;

    private Result(boolean success, T value, E error)
    {
        this.success = success;
        this.value = value;
        this.error = error;
    }

    public static <T, E> Result<T, E> ok(T value)
    {
        return new Result<>(true, value, null);
    }

    public static <T, E> Result<T, E> fail(E error)
    {
        return new Result<>(false, null, error);
    }

    public boolean isFail()
    {
        return !success;
    }

    @JsonIgnore
    public T getValue()
    {
        if (!success) throw new IllegalStateException("流程失败。Result is fail");
        return value;
    }

    @JsonIgnore
    public E getError()
    {
        if (success) throw new IllegalStateException("流程成功。Result is success");
        return error;
    }

    public T getOrThrow()
    {
        if(success) return value;
        throw new IllegalStateException(String.valueOf(error));
    }

    public T getOrDefault(T defaultValue)
    {
        return success ? value : defaultValue;
    }

    public <U> Result<U, E> map(Function<T, U> mapper)
    {
        if (success)
            return Result.ok(mapper.apply(value));
        return Result.fail(error);
    }

    public <U> Result<U, E> flatMap(Function<T, Result<U, E>> mapper)
    {
        return success ? mapper.apply(value) : Result.fail(error);
    }

    public Result<T, E> mapError(Function<E, E> mapper)
    {
        return success ? this : Result.fail(mapper.apply(error));
    }

    @Override
    public String toString()
    {
        return success ? "OK(" + value + ")" : "Fail(" + error + ")";
    }


    // 改getValueDirectly 为 getValueOrThrow
    // 改handleIfExist为map
    // 加    public <U> Maybe<U> flatMap(Function<T,Maybe<U>> mapper)
    //    {
    //        return isValid() ? mapper.apply(value) : Maybe.nothing();
    //    }
}
package Autocomplete;

import java.util.List;

/**
 * Created by gfitas on 04/12/15.
 */
public abstract class Action {
    public abstract List<? extends Object> methodForGettingItem(String search);
    public abstract void methodWhenAnItemIsSelected(Object object);
}

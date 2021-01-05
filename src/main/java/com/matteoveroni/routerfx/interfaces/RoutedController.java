package com.matteoveroni.routerfx.interfaces;

import com.matteoveroni.routerfx.dto.ExtraData;
import java.util.Optional;

/**
 * @Author Matteo Veroni
 */
public interface RoutedController {

    void routedControllerReady(Optional<ExtraData> dataFromPreviousRoute);
}

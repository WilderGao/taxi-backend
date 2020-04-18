package com.jeychan.taxibackend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 杨世谨
 */
@Data
@AllArgsConstructor
public class DataPowerException extends RuntimeException{
    String message;
}

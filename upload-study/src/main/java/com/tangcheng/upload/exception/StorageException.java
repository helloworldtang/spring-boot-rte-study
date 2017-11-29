package com.tangcheng.upload.exception;

/**
 * @author tangcheng
 * 2017/11/29
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

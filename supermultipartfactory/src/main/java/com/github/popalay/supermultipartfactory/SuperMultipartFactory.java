package com.github.popalay.supermultipartfactory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.webkit.MimeTypeMap;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public final class SuperMultipartFactory {

    private final static String PART_POSTFIX = "]";
    private final static String PART_PREFIX = "[";

    private final Object mObject;

    @NonNull
    public static SuperMultipartFactory create(@NonNull Object object) {
        return new SuperMultipartFactory(object);
    }

    @WorkerThread
    public MultipartBody getMultipartBody() {
        if (!mObject.getClass().isAnnotationPresent(Partable.class)) {
            throw new IllegalArgumentException(mObject.getClass().getSimpleName() + " must has Partable annotation");
        }

        final String partName = mObject.getClass().getAnnotation(Partable.class).value();

        final List<MultipartBody.Part> parts = generateParts(partName, mObject, null, null, false);
        final MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (MultipartBody.Part part : parts) {
            requestBodyBuilder.addPart(part);
        }
        return requestBodyBuilder.build();
    }

    private SuperMultipartFactory(@NonNull Object object) {
        mObject = object;
    }

    private List<MultipartBody.Part> generateParts(String partName, @NonNull Object object,
            @Nullable String parentPrefix, @Nullable String parentPostfix, boolean isListItem) {

        partName += isListItem ? PART_PREFIX + PART_POSTFIX : PART_PREFIX;
        final List<MultipartBody.Part> parts = new ArrayList<>();

        if (object instanceof File) {
            parts.add(putFile(partName, (File) object));
            return parts;
        }

        String prefix = "";
        String postfix = "";
        if (partName.length() > 0) {
            prefix = parentPrefix == null ? partName : parentPrefix + partName;
            postfix = parentPostfix == null ? PART_POSTFIX : parentPostfix + PART_POSTFIX;
        }
        final Field[] fields = object.getClass().getDeclaredFields();

        SerializedName serialName;
        Object value;
        for (Field field : fields) {
            value = getFieldValue(object, field);
            serialName = field.getAnnotation(SerializedName.class);
            if (value != null) {
                if (serialName != null) {
                    if (field.isAnnotationPresent(Partable.class)) {
                        parts.addAll(generateParts(serialName.value(), value, prefix, postfix, false));
                    } else if (value instanceof List) {
                        for (Object o : ((List) value)) {
                            parts.addAll(generateParts(serialName.value(), o, prefix, postfix, true));
                        }
                    } else if (value instanceof File) {
                        parts.add(putFile(partName, (File) value));
                    } else {
                        parts.add(MultipartBody.Part.createFormData(prefix + serialName.value() + postfix,
                                value.toString()));
                    }
                }
            } else {
                if (serialName != null) {
                    parts.add(MultipartBody.Part.createFormData(prefix + serialName.value() + postfix, null,
                            RequestBody.create(null, new byte[] {})));
                }
            }
        }
        return parts;
    }

    @Nullable
    private Object getFieldValue(@NonNull Object object, @NonNull Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private String getMimeType(File file) {
        String type = null;
        final String extension = MimeTypeMap.getFileExtensionFromUrl(file.getPath());
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private MultipartBody.Part putFile(String name, File file) {
        return MultipartBody.Part.createFormData(name, file.getName(),
                RequestBody.create(MediaType.parse(getMimeType(file)), file));
    }
}

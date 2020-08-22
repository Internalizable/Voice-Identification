package me.internalizable.VoiceIdentification.recording;

import lombok.Getter;
import lombok.Setter;

public enum RecordingState {

    NOT_RECORDING, RECORDING;

    @Getter @Setter public static RecordingState state;
}

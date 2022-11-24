package com.godtiner.api.domain.member;

import com.godtiner.api.global.exception.UnsupportedImageFormatException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String uniqueName;

    @Column
    private String originName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member; // 1

    private final static String supportedExtension[] = {"jpg", "jpeg", "gif", "bmp", "png"}; // 2

    public ProfileImage(String originName) {
        this.uniqueName = generateUniqueName(extractExtension(originName)); // 3
        this.originName = originName;
    }

    public void initPost(Member member) { // 4
        if(this.member == null) {
            this.member = member;
        }
    }

    private String generateUniqueName(String extension) { // 5
        return UUID.randomUUID().toString() + "." + extension;
    }

    private String extractExtension(String originName) { // 6
        try {
            String ext = originName.substring(originName.lastIndexOf(".") + 1);
            if(isSupportedFormat(ext)) return ext;
        } catch (StringIndexOutOfBoundsException e) { }
        throw new UnsupportedImageFormatException();
    }

    private boolean isSupportedFormat(String ext) { // 7
        return Arrays.stream(supportedExtension).anyMatch(e -> e.equalsIgnoreCase(ext));
    }
}

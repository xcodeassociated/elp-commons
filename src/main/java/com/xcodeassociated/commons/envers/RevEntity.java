package com.xcodeassociated.commons.envers;

import com.xcodeassociated.commons.config.audit.RevListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@RevisionEntity(RevListener.class)
public class RevEntity extends DefaultRevisionEntity {

    private String username;

}

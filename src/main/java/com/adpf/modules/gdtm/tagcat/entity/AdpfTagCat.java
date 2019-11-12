package com.adpf.modules.gdtm.tagcat.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * 角色权限关联表
 * @author zzd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adpf_tag_bindings")
public class AdpfTagCat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @Column(name = "tag_id", unique = true, nullable = false, precision = 10, scale = 0)
    private Long tag_id;

    @Id
    @NonNull
    @Column(name = "cat_id", unique = true, nullable = false, precision = 10, scale = 0)
    private Long cat_id;

}

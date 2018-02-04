package com.example.xmlprocessor.idgenerator;

import com.example.xmlprocessor.controller.DocumentController;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class IdOrGenerated extends IdentityGenerator{

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Override
    public Serializable generate(SessionImplementor s, Object obj) throws HibernateException {
        if (obj == null) throw new HibernateException(new NullPointerException());

        Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
        return id != null ? id : super.generate(s, obj);
    }
}

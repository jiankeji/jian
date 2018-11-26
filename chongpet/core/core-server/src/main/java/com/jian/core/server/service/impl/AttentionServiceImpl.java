package com.jian.core.server.service.impl;

import com.jian.core.model.bean.Attention;
import com.jian.core.server.dao.AttentionDao;
import com.jian.core.server.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户关注 取关service实现
 * @author shen
 *
 */
//@Service
//@Transactional
@SuppressWarnings("ALL")
@Component
public class AttentionServiceImpl implements AttentionService {
		
	@Autowired
	private AttentionDao attentionDao;

	@Override
	public int attention(Attention attention) {
		Integer status;
		try {
			status = attentionDao.selectattstatus1(attention);
			if(status==0 || status==2) {//已经关注过了 返回-1
				return -1; 
			}
			attention.setStatus(2);
			attentionDao.addAttention2(attention);//关注
			attentionDao.addfans(attention.getUserIda());
		} catch (Exception e) {
			status=-1;
		}
		
		if(status==-1) {
			try {
				status = attentionDao.selectattstatus2(attention);
				if(status==1 || status==2) {//已经关注过了
					return -1; 
				}
				attention.setStatus(2);
				attentionDao.addAttention3(attention);//关注  
				attentionDao.addfans(attention.getUserIda());
			} catch (Exception e) {
				status = -2;
			}
		}
		
		if(status==-2) {
			attention.setStatus(0);
			attentionDao.addAttention(attention);//关注  没有粉丝关系
			attentionDao.addfans(attention.getUserIda());
		}
		return 0;
	}
	
	@Override
	public int cancelAttention(Attention attention) {
		Integer status;
		
		try {
			status = attentionDao.selectattstatus1(attention);
			if(status==0) {//表里的b关注了a
				attentionDao.cancelAttention1(attention);
				attentionDao.deletefans(attention.getUserIda());
			}
			if(status==2) {//互相关注的情况
				attention.setStatus(1);
				attentionDao.addAttention2(attention);
				attentionDao.deletefans(attention.getUserIda());
			}
			if(status==1) {
				return -1;
			}
		} catch (Exception e) {
			status = -1;
		}
		
		if (status==-1) {
			try {
				status = attentionDao.selectattstatus2(attention);
				if(status==1) {//表里的a关注了b
					attentionDao.cancelAttention2(attention);
					attentionDao.deletefans(attention.getUserIda());
				}
				if(status==2) {//互相关注的情况
					attention.setStatus(0);
					attentionDao.addAttention2(attention);
					attentionDao.deletefans(attention.getUserIda());
				}
				if(status==0) {
					return -1;
				}
			} catch (Exception e) {
				return -1;
			}
		}
		
		return 0;
	}
	
	
}






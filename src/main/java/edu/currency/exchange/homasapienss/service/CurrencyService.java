package edu.currency.exchange.homasapienss.service;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.dto.CurrencyDTO;
import edu.currency.exchange.homasapienss.entities.Currency;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;
import edu.currency.exchange.homasapienss.exceptions.exists.CurrencyExistsException;
import edu.currency.exchange.homasapienss.exceptions.notfound.CurrencyNotFoundException;
import edu.currency.exchange.homasapienss.mappers.CurrencyMapper;

import java.sql.SQLException;
import java.util.List;

public class CurrencyService {
    private final CurrencyDAO currencyDAO = new CurrencyDAO();
    private final CurrencyMapper currencyMapper = new CurrencyMapper();

    public CurrencyDTO getById(int id) {
        try {
            return currencyDAO.getById(id)
                    .map(currencyMapper::toDTO)
                    .orElseThrow(CurrencyNotFoundException::new);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdByCode(String code) {
        try {
            return currencyDAO.getByCode(code)
                    .orElseThrow(CurrencyNotFoundException::new)
                    .getId();
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public CurrencyDTO getByCode(String code) {
        try {
            return currencyDAO.getByCode(code)
                    .map(currencyMapper::toDTO)
                    .orElseThrow(CurrencyNotFoundException::new);
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public List<CurrencyDTO> getAll() {
        try {
            return currencyDAO.getAll()
                    .stream().map(currencyMapper::toDTO)
                    .toList();
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public void save(CurrencyDTO currencyDTO) {
        try {
            Currency currency = currencyMapper.toEntity(currencyDTO);
            Integer savedRows = currencyDAO.save(currency);
            if (savedRows == 0) {
                throw new CurrencyExistsException();
            }
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public void update(CurrencyDTO currencyDTO) {
        try {
            Currency currency = currencyMapper.toEntity(currencyDTO);
            Integer updatedRows = currencyDAO.update(currency);
            if (updatedRows == 0) throw new CurrencyNotFoundException();
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }

    public void delete(Integer id) {
        try {
            int deletedRows = currencyDAO.delete(id);
            if (deletedRows == 0) {
                throw new CurrencyNotFoundException();
            }
        } catch (SQLException e) {
            throw new ApplicationException(ErrorMessage.ERROR);
        }
    }
}

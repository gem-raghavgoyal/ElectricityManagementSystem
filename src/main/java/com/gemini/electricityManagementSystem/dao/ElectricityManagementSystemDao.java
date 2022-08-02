package com.gemini.electricityManagementSystem.dao;

import com.gemini.electricityManagementSystem.model.Grievance;
import com.gemini.electricityManagementSystem.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ElectricityManagementSystemDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElectricityManagementSystemDao.class);

    private static final String ID_PARAM = "Id";
    private static final String NAME_PARAM = "Name";
    private static final String ADDRESS_PARAM = "Address";
    private static final String PINCODE_PARAM = "Pincode";
    private static final String CITY_PARAM = "City";
    private static final String CUSTOMER_ID_PARAM = "CustomerId";
    private static final String SUBJECT_PARAM = "Subject";
    private static final String SUMMARY_PARAM = "Summary";
    private static final String GRIEVANCE_ID_PARAM = "GrievanceId";
    private static final String CREATE_USER = "INSERT INTO Customer(ID,Name,Address,City,Pincode) VALUES(" +
            ":" + ID_PARAM + "" +
            ":" + NAME_PARAM + "" +
            ":" + ADDRESS_PARAM + "" +
            ":" + CITY_PARAM + "" +
            ":" + PINCODE_PARAM + ")";
    private static final String REGISTER_GRIEVANCE = "INSERT INTO Grievance(GrievanceId,CustomerId,Subject,Summary) VALUES(" +
            ":" + GRIEVANCE_ID_PARAM + "" +
            ":" + CUSTOMER_ID_PARAM + "" +
            ":" + SUBJECT_PARAM + "" +
            ":" + SUMMARY_PARAM;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public ElectricityManagementSystemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * method to save new user info into db
     * @param userDetails obj
     * @param customerId for user
     * @return
     */
    public Boolean createUser(UserDetails userDetails, UUID customerId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(ID_PARAM, customerId)
                .addValue(NAME_PARAM, userDetails.getName())
                .addValue(ADDRESS_PARAM, userDetails.getAddress())
                .addValue(CITY_PARAM, userDetails.getCity())
                .addValue(PINCODE_PARAM, userDetails.getPincode());
        LOGGER.info("Registering user- {}", parameterSource);
        try {
            namedParameterJdbcTemplate.update(CREATE_USER, parameterSource);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error encountered while registering user {}", e.getMessage());
            return false;
        }
    }

    /**
     * method to save new grievance info into db
     * @param grievance obj
     * @param grievanceId for grievance
     * @return
     */
    public Boolean registerGrievance(Grievance grievance, UUID grievanceId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(GRIEVANCE_ID_PARAM, grievanceId)
                .addValue(CUSTOMER_ID_PARAM, grievance.getCustomerId())
                .addValue(SUBJECT_PARAM, grievance.getSubject())
                .addValue(SUMMARY_PARAM, grievance.getSummery());
        LOGGER.info("Submitting grievance with params {}", parameterSource);
        try {
            namedParameterJdbcTemplate.update(REGISTER_GRIEVANCE, parameterSource);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error while submitting the grievance {}", e.getMessage());
            return false;
        }
    }
}
